package eseo.cpoo.jfx.echecpointexe.model.article;

public class Template extends Article {

    private String firstColor;

    private String secondColor;

    public Template(String name, int price, String firstColor, String secondColor) {
        this.name = name;
        this.price = price;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public String getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(String firstColor) {
        this.firstColor = firstColor;
    }

    public String getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(String secondColor) {
        this.secondColor = secondColor;
    }
}
