package com.mahmoudjoe3.wasfaty.pojo;

public class CommentPost {
    private int Id;
    private int UserId;
    private int RecipeId;
    private boolean IsCreator;
    private long CreatedData;
    private String ReviewText;
    private String ReviewResult;

    public CommentPost(Comment comment, double commentPolarity) {
        this.Id = comment.getId();
        this.UserId = comment.getUserId();
        this.RecipeId = comment.getRecipeId();
        this.IsCreator = comment.isCreator();
        this.CreatedData = comment.getCreatedTime();
        this.ReviewText = comment.getCommentText();
        this.ReviewResult =  String.valueOf(commentPolarity);
    }



    @Override
    public String toString() {
        return "CommentPost{" +
                "id=" + Id +
                ", userId=" + UserId +
                ", recipeId=" + RecipeId +
                ", creator=" + IsCreator +
                ", createdTime=" + CreatedData +
                ", commentText='" + ReviewText + '\'' +
                ", reviewResult=" + ReviewResult +
                '}';
    }


}
