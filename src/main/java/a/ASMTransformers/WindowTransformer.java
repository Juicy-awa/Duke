package a.ASMTransformers;

import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.utils.shader.impl.KawaseBlur;
import com.catclient.duke.utils.skia.context.SkiaContext;
import com.mojang.blaze3d.platform.Window;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * @Author：jiuxian_baka
 * @Date：2025/12/6
 * @Filename：WindowTransformer
 */
public class WindowTransformer extends ASMTransformer {
    public WindowTransformer() {
        super(Window.class);
    }

    public static void onSizeChange(int width, int height) {
        SkiaContext.createSurface(width, height);
        KawaseBlur.GUI_BLUR.resize();
        KawaseBlur.INGAME_BLUR.resize();
    }

    @Inject(method = "onResize", desc = "(JII)V")
    public void onResize(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        for (AbstractInsnNode instruction : array) {
            if (instruction.getOpcode() == Opcodes.RETURN) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                list.add(new VarInsnNode(Opcodes.ILOAD, 4));
                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(WindowTransformer.class), "onSizeChange", "(II)V", false));
                methodNode.instructions.insertBefore(instruction, list);
            }
        }
    }
}
