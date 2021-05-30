package com.mahmoudjoe3.wasfa.pojo;

import java.util.List;

public class NLP_API_response {

    /**
     * documentSentiment : {"magnitude":0.8,"score":0.8}
     * language : en
     * sentences : [{"sentiment":{"magnitude":0.8,"score":0.8},"text":{"beginOffset":-1,"content":"good"}}]
     */

    private DocumentSentimentBean documentSentiment;
    private String language;
    private List<SentencesBean> sentences;

    public DocumentSentimentBean getDocumentSentiment() {
        return documentSentiment;
    }

    public void setDocumentSentiment(DocumentSentimentBean documentSentiment) {
        this.documentSentiment = documentSentiment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<SentencesBean> getSentences() {
        return sentences;
    }

    public void setSentences(List<SentencesBean> sentences) {
        this.sentences = sentences;
    }

    public static class DocumentSentimentBean {
        /**
         * magnitude : 0.8
         * score : 0.8
         */

        private double magnitude;
        private double score;

        public double getMagnitude() {
            return magnitude;
        }

        public void setMagnitude(double magnitude) {
            this.magnitude = magnitude;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    public static class SentencesBean {
        /**
         * sentiment : {"magnitude":0.8,"score":0.8}
         * text : {"beginOffset":-1,"content":"good"}
         */

        private SentimentBean sentiment;
        private TextBean text;

        public SentimentBean getSentiment() {
            return sentiment;
        }

        public void setSentiment(SentimentBean sentiment) {
            this.sentiment = sentiment;
        }

        public TextBean getText() {
            return text;
        }

        public void setText(TextBean text) {
            this.text = text;
        }

        public static class SentimentBean {
            /**
             * magnitude : 0.8
             * score : 0.8
             */

            private double magnitude;
            private double score;

            public double getMagnitude() {
                return magnitude;
            }

            public void setMagnitude(double magnitude) {
                this.magnitude = magnitude;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }
        }

        public static class TextBean {
            /**
             * beginOffset : -1
             * content : good
             */

            private int beginOffset;
            private String content;

            public int getBeginOffset() {
                return beginOffset;
            }

            public void setBeginOffset(int beginOffset) {
                this.beginOffset = beginOffset;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
