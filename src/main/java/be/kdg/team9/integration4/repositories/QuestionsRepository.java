package be.kdg.team9.integration4.repositories;

import be.kdg.team9.integration4.model.Survey;
import be.kdg.team9.integration4.model.question.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long>, FindAllQuestionBySurveyId {
    @Query("SELECT questions FROM Question questions")
    List<Question> findAllQuestions();

    List<Question> getQuestionsBySurvey(Survey survey);

    @Modifying
    @Query(value = "INSERT INTO question(question_name, question_type, survey_id) VALUES (:questionName, :questionType, :surveyId)", nativeQuery = true)
    void insertQuestions(String questionName, String questionType, Long surveyId);

    @Modifying
    @Query(value = "INSERT INTO question(question_name, question_type, survey_id, min, max, step) VALUES (:questionName, :questionType, :surveyId, :min, :max, :step)", nativeQuery = true)
    void insertRangeQuestion(String questionName, String questionType, Long surveyId, Integer min, Integer max, Integer step);

    @Modifying
    @Query(value = "INSERT INTO question(question_name, question_type, survey_id, is_multi_choice) VALUES (:questionName, :questionType, :surveyId, :isMultiChoice)", nativeQuery = true)
    void insertChoiceQuestion(String questionName, String questionType, Long surveyId, Boolean isMultiChoice);


    @Transactional
    void deleteQuestionsBySurvey(Survey survey);

    @Transactional
    void delete(Question question);

//    @Transactional
//    void deleteAll(List<Question> questions);
}
