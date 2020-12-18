package tjc.rug.ExpertSystem.model;

public class Output extends AbstractKnowledge {

    private String description;
    private String section;
    private String link = null;
    private String url = null;


    public Output() {
        super();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getUrl() {
        return url;
    }

    public String toString() {
        String out = description + "\n" + section + "\n";
        if (link != null) out += link + "\n" + url + "\n";
        return out;
    }
}
