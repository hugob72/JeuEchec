package eseo.cpoo.jfx.echecpointexe.model.article;

public class Avatar extends Article {

    private String pathRessource;

    public Avatar(String name, int price, String pathRessource) {
        this.name = name;
        this.price = price;
        this.pathRessource = pathRessource;
    }

    public String getPathRessource() {
        return pathRessource;
    }

    public void setPathRessource(String pathRessource) {
        this.pathRessource = pathRessource;
    }
}
