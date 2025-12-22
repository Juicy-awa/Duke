package a.ASMTransformers;

import com.catclient.duke.asm.api.ASMTransformer;
import com.catclient.duke.asm.api.annotations.Inject;
import com.catclient.duke.utils.Animation.AnimationUtils;
import com.catclient.duke.utils.shader.impl.KawaseBlur;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MinecraftTransformer extends ASMTransformer {
    private static long lastFrameMs;

    public MinecraftTransformer() {
        super(Minecraft.class);
    }

    public static void resizeHook() {
        KawaseBlur.GUI_BLUR.resize();
        KawaseBlur.INGAME_BLUR.resize();
    }

    public static void runTickHead() {
        long currentTime = System.nanoTime() / 1_000_000L;
        int deltaTime = (int) (currentTime - lastFrameMs);
        lastFrameMs = currentTime;
        AnimationUtils.delta = deltaTime;
    }

    @Inject(method = "resizeDisplay", desc = "()V")
    public void resizeDisplay(MethodNode methodNode) {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftTransformer.class), "resizeHook", "()V", false));
        methodNode.instructions.insert(list);
    }

    @Inject(method = "runTick", desc = "(Z)V")
    public void runTick(MethodNode methodNode) {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(MinecraftTransformer.class), "runTickHead", "()V", false));
        methodNode.instructions.insert(list);
    }
}
