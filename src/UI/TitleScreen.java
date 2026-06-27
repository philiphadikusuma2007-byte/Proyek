package UI;

import Game.Game;
import Game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TitleScreen — 8-bit EVOMON title screen.
 * Shown before the game loads. Press ENTER to open menu, then choose PLAY or EXIT.
 * Transitions to GameState.World on PLAY.
 */
public class TitleScreen {

    private final GamePanel gp;

    // ── Sub-components ────────────────────────────────────────────────────────
    private final StarField      starField;
    private final PixelCreature  creature;
    private final List<String>   menuLabels;

    // ── State ─────────────────────────────────────────────────────────────────
    private int     selectedIndex = 0;
    private boolean showMenu      = false;

    // ── Animation ─────────────────────────────────────────────────────────────
    private int   titleTick = 0;
    private float titleBobY = 0f;
    private int   blinkTick = 0;
    private boolean blinkOn = true;

    // ── Colours ───────────────────────────────────────────────────────────────
    private static final Color BG_TOP      = new Color( 10,  10,  40);
    private static final Color BG_BOTTOM   = new Color(  5,   5,  20);
    private static final Color TITLE_GOLD  = new Color(255, 220,  40);
    private static final Color TITLE_SHADE = new Color(180,  80,   0);
    private static final Color SUBTITLE_C  = new Color(120, 200, 255);
    private static final Color PROMPT_C    = new Color(200, 200, 200);
    private static final Color BORDER_C    = new Color( 60, 120, 220);
    private static final Color PANEL_BG    = new Color(  0,   0,   0, 170);
    private static final Color SEL_COLOR   = new Color(255, 255,  80);
    private static final Color UNSEL_COLOR = new Color(200, 200, 200);
    private static final Color SHADOW_C    = new Color( 50,  50,  50);

    // ── Fonts ─────────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE    = new Font("Courier New", Font.BOLD,  72);
    private static final Font FONT_SUBTITLE = new Font("Courier New", Font.BOLD,  18);
    private static final Font FONT_MENU     = new Font("Courier New", Font.BOLD,  28);
    private static final Font FONT_PROMPT   = new Font("Courier New", Font.PLAIN, 14);
    private static final Font FONT_VER      = new Font("Courier New", Font.PLAIN, 11);

    // ─────────────────────────────────────────────────────────────────────────

    public TitleScreen(GamePanel gp) {
        this.gp = gp;

        starField = new StarField(Game.screenWidth, Game.screenHeight);

        int ps = 14; // pixel size for sprite
        creature = new PixelCreature(
            (Game.screenWidth  - 16 * ps) / 2,
            (Game.screenHeight - 16 * ps) / 2 - 20,
            ps
        );

        menuLabels = new ArrayList<>();
        menuLabels.add("▶  PLAY");
        menuLabels.add("✕  EXIT");
    }

    // ── Update (called every frame by GamePanel) ──────────────────────────────

    public void update() {
        starField.update();
        creature.update();

        titleTick++;
        titleBobY = (float) Math.sin(titleTick * 0.03) * 5f;

        blinkTick++;
        if (blinkTick >= 30) { blinkOn = !blinkOn; blinkTick = 0; }
    }

    // ── Draw (called every frame by GamePanel) ────────────────────────────────

    public void draw(Graphics2D g2) {
        // Pixel-crisp rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        drawBackground(g2);
        starField.draw(g2);
        drawBorder(g2);
        creature.draw(g2);
        drawTitle(g2);
        drawSubtitle(g2);

        if (showMenu) drawMenu(g2);
        else          drawPressEnter(g2);

        drawVersion(g2);
    }

    // ── Input (called by GamePanel.keyPressed) ────────────────────────────────

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!showMenu) {
            if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE)
                showMenu = true;
            return;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) navigate(-1);
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) navigate(1);
        else if (key == KeyEvent.VK_ESCAPE) showMenu = false;
        else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) confirm();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void navigate(int dir) {
        selectedIndex = (selectedIndex + dir + menuLabels.size()) % menuLabels.size();
    }

    private void confirm() {
        if (selectedIndex == 0) {
            // PLAY — hand off to the world
            gp.currentState = GameState.World;
        } else if (selectedIndex == 1) {
            // EXIT
            System.exit(0);
        }
    }

    private void drawBackground(Graphics2D g2) {
        GradientPaint gp2 = new GradientPaint(
            0, 0,                BG_TOP,
            0, Game.screenHeight, BG_BOTTOM
        );
        g2.setPaint(gp2);
        g2.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
    }

    private void drawBorder(Graphics2D g2) {
        g2.setColor(BORDER_C);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(8, 8, Game.screenWidth - 16, Game.screenHeight - 16);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(14, 14, Game.screenWidth - 28, Game.screenHeight - 28);

        // Gold corner squares
        int cs = 10;
        g2.setColor(TITLE_GOLD);
        g2.fillRect(8,                   8,                    cs, cs);
        g2.fillRect(Game.screenWidth-8-cs, 8,                    cs, cs);
        g2.fillRect(8,                   Game.screenHeight-8-cs, cs, cs);
        g2.fillRect(Game.screenWidth-8-cs, Game.screenHeight-8-cs, cs, cs);
        g2.setStroke(new BasicStroke(1));
    }

    private void drawTitle(Graphics2D g2) {
        String text = "EVOMON";
        g2.setFont(FONT_TITLE);
        FontMetrics fm = g2.getFontMetrics();
        int tx = (Game.screenWidth - fm.stringWidth(text)) / 2;
        int ty = 110 + (int) titleBobY;

        // Layered pixel shadow
        for (int d = 6; d >= 1; d--) {
            g2.setColor(TITLE_SHADE.darker());
            g2.drawString(text, tx + d, ty + d);
        }
        g2.setColor(TITLE_SHADE);
        g2.drawString(text, tx + 4, ty + 4);
        g2.setColor(TITLE_GOLD);
        g2.drawString(text, tx, ty);
    }

    private void drawSubtitle(Graphics2D g2) {
        String sub = "─── VERSION 1.0  ·  8-BIT EDITION ───";
        g2.setFont(FONT_SUBTITLE);
        g2.setColor(SUBTITLE_C);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(sub, (Game.screenWidth - fm.stringWidth(sub)) / 2, 140);
    }

    private void drawPressEnter(Graphics2D g2) {
        if (!blinkOn) return;
        String prompt = "─  PRESS  ENTER  TO  START  ─";
        g2.setFont(FONT_PROMPT);
        g2.setColor(PROMPT_C);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(prompt, (Game.screenWidth - fm.stringWidth(prompt)) / 2, Game.screenHeight - 80);
    }

    private void drawMenu(Graphics2D g2) {
        int panelW = 280, panelH = 130;
        int panelX = (Game.screenWidth - panelW) / 2;
        int panelY = Game.screenHeight - 220;

        // Panel background
        g2.setColor(PANEL_BG);
        g2.fillRoundRect(panelX, panelY, panelW, panelH, 12, 12);
        g2.setColor(BORDER_C);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(panelX, panelY, panelW, panelH, 12, 12);
        g2.setStroke(new BasicStroke(1));

        g2.setFont(FONT_MENU);
        FontMetrics fm = g2.getFontMetrics();

        int itemX = (Game.screenWidth - fm.stringWidth(menuLabels.get(0))) / 2;
        int[] itemY = { Game.screenHeight - 180, Game.screenHeight - 130 };

        for (int i = 0; i < menuLabels.size(); i++) {
            String label    = menuLabels.get(i);
            boolean isSelected = (i == selectedIndex);

            // Shadow
            g2.setColor(SHADOW_C);
            g2.drawString(label, itemX + 3, itemY[i] + 3);

            // Label
            g2.setColor(isSelected ? SEL_COLOR : UNSEL_COLOR);
            g2.drawString(label, itemX, itemY[i]);

            // Arrow cursor
            if (isSelected) {
                g2.setColor(SEL_COLOR);
                int[] ax = { itemX - 30, itemX - 15, itemX - 15 };
                int[] ay = { itemY[i] - 10, itemY[i] - 18, itemY[i] - 2 };
                g2.fillPolygon(ax, ay, 3);
            }
        }
    }

    private void drawVersion(Graphics2D g2) {
        g2.setFont(FONT_VER);
        g2.setColor(new Color(100, 100, 130));
        g2.drawString("© 2025 EVOMON STUDIOS", 24, Game.screenHeight - 24);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Inner helper classes (kept here so TitleScreen is self-contained)
    // ══════════════════════════════════════════════════════════════════════════

    /** Scrolling pixel star field. */
    private static class StarField {
        private static final int COUNT = 80;
        private final int width, height;
        private final int[] x, y, size, speed;
        private final Color[] color;

        StarField(int w, int h) {
            width = w; height = h;
            x = new int[COUNT]; y = new int[COUNT];
            size = new int[COUNT]; speed = new int[COUNT];
            color = new Color[COUNT];
            Random rng = new Random(42);
            for (int i = 0; i < COUNT; i++) {
                x[i]     = rng.nextInt(w);
                y[i]     = rng.nextInt(h);
                size[i]  = rng.nextInt(3) + 1;
                speed[i] = rng.nextInt(2) + 1;
                int b    = 150 + rng.nextInt(106);
                color[i] = new Color(b, b, b);
            }
        }

        void update() {
            for (int i = 0; i < COUNT; i++) {
                y[i] += speed[i];
                if (y[i] > height) { y[i] = 0; x[i] = (int)(Math.random() * width); }
            }
        }

        void draw(Graphics2D g2) {
            for (int i = 0; i < COUNT; i++) {
                g2.setColor(color[i]);
                g2.fillRect(x[i], y[i], size[i], size[i]);
            }
        }
    }

    /** 16×16 pixel-art creature with bob animation. */
    private static class PixelCreature {
        private final int ox, oy, ps;
        private float bobAngle = 0f;

        private static final int[][] SPRITE = {
            {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
            {0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0},
            {0,0,1,2,2,2,2,2,2,2,2,2,2,1,0,0},
            {0,1,2,2,2,3,2,2,2,2,3,2,2,2,1,0},
            {0,1,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
            {0,1,2,2,2,2,2,1,1,2,2,2,2,2,1,0},
            {0,0,1,2,2,2,2,2,2,2,2,2,2,1,0,0},
            {0,0,0,1,2,2,2,2,2,2,2,2,1,0,0,0},
            {0,0,1,2,2,2,2,2,2,2,2,2,2,1,0,0},
            {0,1,2,2,1,2,2,2,2,2,2,1,2,2,1,0},
            {1,2,2,1,0,1,2,2,2,2,1,0,1,2,2,1},
            {1,2,1,0,0,0,1,2,2,1,0,0,0,1,2,1},
            {0,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        };
        private static final Color C_OUT = new Color(30,  30,  30);
        private static final Color C_BOD = new Color(80, 160, 255);
        private static final Color C_ACC = new Color(255, 230, 50);

        PixelCreature(int ox, int oy, int ps) { this.ox=ox; this.oy=oy; this.ps=ps; }

        void update() { bobAngle += 0.05f; }

        void draw(Graphics2D g2) {
            int drawY = oy + (int)(Math.sin(bobAngle) * 6f);
            for (int r = 0; r < SPRITE.length; r++)
                for (int c = 0; c < SPRITE[r].length; c++) {
                    Color col = null;
                        if (SPRITE[r][c] == 1) col = C_OUT;
                        else if (SPRITE[r][c] == 2) col = C_BOD;
                        else if (SPRITE[r][c] == 3) col = C_ACC;
                    if (col == null) continue;
                    g2.setColor(col);
                    g2.fillRect(ox + c*ps, drawY + r*ps, ps, ps);
                }
        }
    }
}
