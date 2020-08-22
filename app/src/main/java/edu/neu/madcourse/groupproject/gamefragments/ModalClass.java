package edu.neu.madcourse.groupproject.gamefragments;

public class ModalClass {

    int image;
    String text;
    int progress;

    public ModalClass(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public ModalClass(int image, String text, int progress) {
        this.image = image;
        this.text = text;
        this.progress = progress;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
