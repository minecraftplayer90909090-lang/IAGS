/*
 * In-Game Account Switcher is a mod for Minecraft that allows you to change your logged in account in-game, without restarting Minecraft.
 * Copyright (C) 2015-2022 The_Fireplace
 * Copyright (C) 2021-2026 VidTu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package ru.vidtu.ias.screen;

//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else
/*import net.minecraft.client.gui.GuiGraphics;*/

/**
 * Shared glassmorphism rendering helpers used across every IAS screen.
 *
 * <p>Everything here is built only out of plain {@code fill} calls (no shaders,
 * no off-screen blur passes, no extra render targets, no NanoVG-style native
 * dependency). That's intentional: it has to look right and stay cheap on
 * software/GLES-style mobile GPU stacks (e.g. Android via GL4ES/ANGLE/Mobox),
 * where a real post-process blur pipeline may silently no-op or isn't worth
 * the cost. The "frosted glass" look is faked with a translucent fill, a
 * faint top sheen and a soft cool-toned border instead.</p>
 *
 * @author VidTu
 */
final class GlassUI {
    /**
     * Base translucent glass fill (dark navy, ~70% opaque).
     */
    static final int FILL = 0xB3_12_16_28;

    /**
     * Slightly lighter glass fill, used for hovered/selected surfaces.
     */
    static final int FILL_HOVER = 0xCC_1B_22_40;

    /**
     * Soft cool-white/cyan border tone.
     */
    static final int BORDER = 0x66_8A_9D_FF;

    /**
     * Brighter border tone, used for the active/selected/focused element.
     */
    static final int BORDER_BRIGHT = 0xCC_8A_9D_FF;

    /**
     * Indigo accent, used for primary actions and the selection rail.
     */
    static final int ACCENT = 0xFF_6C_5C_E7;

    /**
     * Faint top sheen, layered over the first few pixels of a panel.
     */
    static final int SHEEN = 0x22_FF_FF_FF;

    /**
     * Dark outline tone for small accents (status dots, etc.), matches the
     * deep navy background so dots read cleanly against any skin texture.
     */
    static final int OUTLINE = 0xFF_0B_0E_1A;

    private GlassUI() {
        // No instances.
    }

    /**
     * Draws a frosted glass card using the default palette.
     *
     * @param graphics Graphics
     * @param x        Card X
     * @param y        Card Y
     * @param width    Card width
     * @param height   Card height
     */
    //? if >=26.1 {
    static void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
    //?} else
    /*static void panel(GuiGraphics graphics, int x, int y, int width, int height) {*/
        panel(graphics, x, y, width, height, FILL, BORDER);
    }

    /**
     * Draws a frosted glass card: translucent fill, soft top sheen and a 1px border.
     *
     * @param graphics Graphics
     * @param x        Card X
     * @param y        Card Y
     * @param width    Card width
     * @param height   Card height
     * @param fill     Fill color (ARGB)
     * @param border   Border color (ARGB)
     */
    //? if >=26.1 {
    static void panel(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int fill, int border) {
    //?} else
    /*static void panel(GuiGraphics graphics, int x, int y, int width, int height, int fill, int border) {*/
        if (width <= 0 || height <= 0) return;
        int x2 = x + width;
        int y2 = y + height;

        // Frosted fill.
        graphics.fill(x, y, x2, y2, fill);

        // Top sheen (fake light catching the "glass").
        int sheen = Math.max(1, Math.min(6, height / 6));
        graphics.fill(x, y, x2, y + sheen, SHEEN);

        // 1px border all around.
        graphics.fill(x, y, x2, y + 1, border);
        graphics.fill(x, y2 - 1, x2, y2, border);
        graphics.fill(x, y, x + 1, y2, border);
        graphics.fill(x2 - 1, y, x2, y2, border);
    }

    /**
     * Draws a glass highlight behind a list row (hover/selection), with a
     * colored accent rail on the left edge.
     *
     * @param graphics Graphics
     * @param x        Row X
     * @param y        Row Y
     * @param width    Row width
     * @param height   Row height
     * @param strong   Whether this is the selected (vs. merely hovered/focused) row
     */
    //? if >=26.1 {
    static void rowHighlight(GuiGraphicsExtractor graphics, int x, int y, int width, int height, boolean strong) {
    //?} else
    /*static void rowHighlight(GuiGraphics graphics, int x, int y, int width, int height, boolean strong) {*/
        if (width <= 0 || height <= 0) return;
        int x2 = x + width;
        int y2 = y + height;
        graphics.fill(x, y, x2, y2, strong ? FILL_HOVER : FILL);
        graphics.fill(x, y, x + 2, y2, strong ? ACCENT : BORDER);
    }

    /**
     * Draws a small status dot at the given top-left position (4x4 box, 2x2 colored core).
     *
     * @param graphics Graphics
     * @param x        Dot X
     * @param y        Dot Y
     * @param color    Core color (ARGB)
     */
    //? if >=26.1 {
    static void dot(GuiGraphicsExtractor graphics, int x, int y, int color) {
    //?} else
    /*static void dot(GuiGraphics graphics, int x, int y, int color) {*/
        graphics.fill(x, y, x + 4, y + 4, OUTLINE);
        graphics.fill(x + 1, y + 1, x + 3, y + 3, color);
    }
}
