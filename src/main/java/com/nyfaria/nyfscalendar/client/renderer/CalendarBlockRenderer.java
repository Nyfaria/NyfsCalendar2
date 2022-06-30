package com.nyfaria.nyfscalendar.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.nyfaria.nyfscalendar.NyfsCalendar;
import com.nyfaria.nyfscalendar.block.entity.CalendarBlockEntity;
import com.nyfaria.nyfscalendar.config.NCConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class CalendarBlockRenderer<T extends CalendarBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation INDICATOR = new ResourceLocation(NyfsCalendar.MODID,"item/indicator");


    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Locale.setDefault(new Locale("es","ES"));
        boolean customCalendar = NCConfig.INSTANCE.useCustomCalendar.get();

        LocalDate currentDate = LocalDate.of(NCConfig.INSTANCE.startYear.get(), NCConfig.INSTANCE.startMonth.get(), NCConfig.INSTANCE.startDay.get()).plusDays(pBlockEntity.getLevel().getDayTime() / 24000L % 2147483647L);
        String currentYear;
        List<String> cMonths;
        List<String> cWeekdays;

        BlockState state  = pBlockEntity.getBlockState();
        Direction direction = state.getValue(BlockStateProperties.FACING);
        int rot = state.getValue(BlockStateProperties.ROTATION_16);

        TextureAtlasSprite sprite3 = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(INDICATOR);
        VertexConsumer builder = pBufferSource.getBuffer(RenderType.cutout());

        String locale1 = Minecraft.getInstance().getLanguageManager().getSelected().getCode().split("_",2)[0];
        String locale2 = Minecraft.getInstance().getLanguageManager().getSelected().getCode().split("_",2)[1];
        Locale locale3 = new Locale(locale1,locale2.toUpperCase());
        String month;
        int startDay ;
        int daysInMonth;
        int dayOfWeek;
        int currentDayOfMonth;
//        NyfDate nyfDate = new NyfDate();
        boolean custom = NCConfig.INSTANCE.useCustomCalendar.get();
//        if(!custom) {
            month = currentDate.getMonth().getDisplayName(TextStyle.FULL, locale3).toUpperCase();
            startDay = currentDate.withDayOfMonth(1).getDayOfWeek().getValue() == 7 ? 0 : currentDate.withDayOfMonth(1).getDayOfWeek().getValue();
            daysInMonth = currentDate.getMonth().length(currentDate.isLeapYear()) + startDay;
            dayOfWeek = currentDate.getDayOfWeek().getValue() == 7 ? 0 : currentDate.getDayOfWeek().getValue();
            currentDayOfMonth = currentDate.getDayOfMonth();
            currentYear = String.valueOf(currentDate.getYear());
//        }
//        else {
//            month = nyfDate.getMonthDisplayName();
//            startDay = (int)nyfDate.getFirstDayOfMonth();
//            daysInMonth = nyfDate.getDaysInMonth(nyfDate.getMonth()) + startDay;
//            dayOfWeek = (int)nyfDate.getDayOfWeek();
//            currentDayOfMonth = (int)nyfDate.getDay();
//            currentYear = String.valueOf(nyfDate.getYear());
//        }


        pPoseStack.pushPose();

        pPoseStack.clear();
        orient(direction, pPoseStack, rot, false);
        float xs = (float) (((dayOfWeek) * 0.11328125));
        float ys = (float) ((Math.floor(((startDay + currentDayOfMonth - 1) / 7)) * 0.11328125));
        pPoseStack.translate(0.7890625 - xs, 0.578125 - ys, 0);
        add(builder, pPoseStack, v(0, 0, 0.874), v(0, 0.1015625, 0.874), v(0.1015625, 0.1015625, 0.874), v(0.1015625, 0, 0.874), sprite3);

        pPoseStack.popPose();

        Font fontRenderer = Minecraft.getInstance().font;

        pPoseStack.pushPose();

        //pPoseStack.translate(1, 1, 0.874);
        orient(direction, pPoseStack, rot, true);
        pPoseStack.scale(0.015f, 0.015f, 1f);
        pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(180));


        fontRenderer.draw(pPoseStack, month, (float) (33.5 - (float)(fontRenderer.width(month)/2)), 3, 0xFF000000);
        pPoseStack.popPose();

        pPoseStack.pushPose();

        //pPoseStack.translate(1, 1, 0.873);
        orient(direction, pPoseStack, rot, true);
        pPoseStack.scale(0.0075f, 0.0075f, 1f);
        pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(180));


        for(int j = 1; j <= 7; j++) {
            int dayNumber;
            String dayOfWeekShort;
//            if(!custom) {
                dayNumber = currentDate.plusDays(j).getDayOfWeek().getValue() + 1 == 8 ? 1 : currentDate.plusDays(j).getDayOfWeek().getValue() + 1;
                dayOfWeekShort = currentDate.plusDays(j).getDayOfWeek().getDisplayName(TextStyle.NARROW, locale3);
//            }
//            else {
//                dayNumber = j;
//                dayOfWeekShort = nyfDate.getWeekDayShort(j - 1);
//            }
            fontRenderer.draw(pPoseStack, dayOfWeekShort, (15 * dayNumber) + 4, 31, 0xFF000000);

        }
        for(int i = 0; i < daysInMonth; i++) {
            if(i - startDay < 0)
            {

            }
            else {
                String textToDraw = String.format(locale3,"%d",i - startDay + 1);
                int len = textToDraw.length() - 1;
                int x = 19 + ((i % 7) * 15) - (len * 3);
                int y = (int)(46 + (Math.floor(i/7) * 15));
                fontRenderer.draw(pPoseStack, textToDraw, x, y, 0xFF000000);
            }
        }

        pPoseStack.popPose();
        String yearDraw = String.format(locale3,"%d",currentDate.getYear());
        pPoseStack.pushPose();

        //pPoseStack.translate(1, 1, 0.873);
        orient(direction, pPoseStack, rot, true);
        pPoseStack.scale(0.01f, 0.01f, 1f);
        pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(180));

        fontRenderer.draw(pPoseStack, currentYear,90 - (fontRenderer.width(yearDraw)),12, 0x80E50000);

        pPoseStack.popPose();
    }

    private static Vec3 v(double x, double y, double z) {
        return new Vec3(x, y, z);
    }
    
    private void add(VertexConsumer renderer, PoseStack stack, Vec3 a, Vec3 b, Vec3 c, Vec3 d, TextureAtlasSprite s) {
        renderer.vertex(stack.last().pose(), (float)a.x, (float)a.y, (float)a.z)
                .color(1.0f, 1.0f, 1.0f, 1.0f).uv(s.getU0(), s.getV1()).uv2((int)s.getU(0), 240).normal(0, 0, 0).endVertex();

        renderer.vertex(stack.last().pose(), (float)b.x, (float)b.y, (float)b.z)
                .color(1.0f, 1.0f, 1.0f, 1.0f).uv(s.getU0(), s.getV0()).uv2(0, 240).normal(0, 0, 0).endVertex();

        renderer.vertex(stack.last().pose(), (float)c.x, (float)c.y, (float)c.z)
                .color(1.0f, 1.0f, 1.0f, 1.0f).uv(s.getU1(), s.getV0()).uv2(0, 240).normal(0, 0, 0).endVertex();

        renderer.vertex(stack.last().pose(), (float)d.x, (float)d.y, (float)d.z)
                .color(1.0f, 1.0f, 1.0f, 1.0f).uv(s.getU1(), s.getV1()).uv2(0, 240).normal(0, 0, 0).endVertex();
    }

    public void orient(Direction direction, PoseStack pPoseStack, int rotation, boolean text) {
        switch(direction) {
            case SOUTH:
                if(text) {
                    pPoseStack.translate(1, 1, 0.873);
                }
                break;
            case NORTH:
                if(text) {
                    pPoseStack.translate(0, 1, 0.127);
                }else {
                    pPoseStack.translate(1 , 0, 1);
                }
                pPoseStack.mulPose(Vector3f.YN.rotationDegrees(180));
                break;
            case EAST:
                if(text) {
                    pPoseStack.translate(0.873, 1, 0);
                }else {
                    pPoseStack.translate(0, 0, 1);
                }
                pPoseStack.mulPose(Vector3f.YN.rotationDegrees(270));
                break;
            case WEST:
                if(text) {
                    pPoseStack.translate(0.127, 1, 1);
                }else {
                    pPoseStack.translate(1, 0, 0);
                }
                pPoseStack.mulPose(Vector3f.YN.rotationDegrees(90));
                break;
            case DOWN:
                switch(rotation)
                {
                    case 8:
                    {
                        if(text) {
                            pPoseStack.translate(1, 0.127, 1);
                        }else {
                            pPoseStack.translate(0, 1, 0);
                        }
                        break;
                    }
                    case 0:
                    {
                        if(text) {
                            pPoseStack.translate(0, 0.127, 0);
                        }else {
                            pPoseStack.translate(1, 1, 1);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                        break;
                    }
                    case 4:
                    {
                        if(text) {
                            pPoseStack.translate(1, 0.127, 0);
                        }else {
                            pPoseStack.translate(0, 1, 1);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                        break;
                    }
                    case 12:
                    {
                        if(text) {
                            pPoseStack.translate(0, 0.127, 1);
                        }else {
                            pPoseStack.translate(1, 1, 0);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                        break;
                    }
                }
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
                break;
            case UP:
                switch(rotation)
                {
                    case 8:
                    {
                        if(text) {
                            pPoseStack.translate(1, 0.873, 0);
                        }else {
                            pPoseStack.translate(0, 0, 1);
                        }
                        break;
                    }
                    case 0:
                    {
                        if(text) {
                            pPoseStack.translate(0, 0.873, 1);
                        }else {
                            pPoseStack.translate(1, 0, 0);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                        break;
                    }
                    case 4:
                    {
                        if(text) {
                            pPoseStack.translate(0, 0.873, 0);
                        }else {
                            pPoseStack.translate(1, 0, 1);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                        break;
                    }
                    case 12:
                    {
                        if(text) {
                            pPoseStack.translate(1, 0.873, 1);
                        }else {
                            pPoseStack.translate(0, 0, 0);
                        }
                        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                        break;
                    }
                }
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(270));
                break;
        }
    }

}
