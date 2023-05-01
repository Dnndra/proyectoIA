package gt.url.edu;

public class Word {
    String tag;
    String value;
    Integer count;

    public Word(String tag, String value, Integer count) {
        this.tag = tag;
        this.value = value;
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
