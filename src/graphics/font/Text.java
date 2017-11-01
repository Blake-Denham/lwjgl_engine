package graphics.font;

import graphics.G2D.Renderable2D;
import graphics.G2D.Renderer2D;
import graphics.Transform;
import graphics.layers.Group;
import math.Mat4;
import math.Vec3;
import math.Vec4;

import java.util.HashMap;

public class Text extends Group {
    private HashMap<Integer, Float> kerningIndices;

    public Text(String word, Font font, Mat4 transform, Vec4 color) {
        super(transform);
        for (int i = 0; i < word.length(); i++) {
            children.add(font.getCharMap().get(word.charAt(i)));
            children.get(i).setColor(color);
        }
        kerningIndices = new HashMap<>();

        for (int i = 0; i < word.length() - 1; i++) {
            char first = word.charAt(i), second = word.charAt(i + 1);
            for (Font.Kerning k : font.getKernings()) {
                if (first == k.getFirst() && second == k.getSecond()) {
                    kerningIndices.put(i + 1, k.getAmount());
                }
            }
        }
    }

    @Override
    public void addChild(Renderable2D renderable) {
        children.add(renderable);
    }

    @Override
    public void submit(Renderer2D renderer) {
        renderer.push(transform);
        for (int i = 0; i < children.size(); i++) {
            Renderable2D r = children.get(i);
            float kerningAmount = 0;
            if(kerningIndices.containsKey(i)){
                kerningAmount = kerningIndices.get(i);
            }
            renderer.push(Transform.initTranslation(new Vec3(kerningAmount,0,0)));
            renderer.submit(r);
            renderer.push(Transform.initTranslation(new Vec3(((Font.Character) r).getXAdvance(), 0, 0)));
        }
        renderer.transformationStackToIdentity();
    }
}
