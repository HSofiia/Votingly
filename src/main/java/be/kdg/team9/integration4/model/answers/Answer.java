package be.kdg.team9.integration4.model.answers;

import be.kdg.team9.integration4.model.question.Question;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "answer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "answer_type", discriminatorType = DiscriminatorType.STRING)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;
    @Column(name = "survey_id")
    private long surveyId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "answer_time")
    private LocalTime answerTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;


    public Answer() {
    }

    public Answer(long surveyId, long userId, Question question, LocalTime answerTime) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.question = question;
        setAnswerTime(LocalTime.now());
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public LocalTime getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(LocalTime answerTime) {
        this.answerTime = answerTime;
    }
}
