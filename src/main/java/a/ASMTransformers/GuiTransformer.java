package a.ASMTransformers;

import com.catclient.duke.Duke;
import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.event.impl.RenderSkiaEvent;
import com.catclient.duke.module.impl.render.HUD;
import com.catclient.duke.utils.shader.impl.KawaseBlur;
import com.catclient.duke.utils.skia.Skia;
import com.catclient.duke.utils.skia.context.SkiaContext;
import com.catclient.duke.utils.wrapper.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/6
 * @Filename：GuiTransformer
 */
public class GuiTransformer extends ASMTransformer implements Wrapper {
    public GuiTransformer() {
        super(Gui.class);
    }

    public static void onRender2D() {
        if (Duke.getInstance().getModuleManager().getModule(HUD.class).blur.get()) {
            KawaseBlur.INGAME_BLUR.draw((int) Duke.getInstance().getModuleManager().getModule(HUD.class).blurIntensity.get());
        }
        try {
            SkiaContext.draw((context) -> {
                Skia.save();
                Skia.scale((float) mc.getWindow().getGuiScale());
                Duke.getInstance().getEventManager().call(new RenderSkiaEvent());
                Skia.restore();
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "renderCrosshair", desc = "(Lnet/minecraft/client/gui/GuiGraphics;)V")
    public void renderCrosshair(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        for (AbstractInsnNode instruction : array) {
            if (instruction.getOpcode() == Opcodes.RETURN) {
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(GuiTransformer.class), "onRender2D", "()V", false));
                methodNode.instructions.insertBefore(instruction, list);
            }
        }
    }
}
