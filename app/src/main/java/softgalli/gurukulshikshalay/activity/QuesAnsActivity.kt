package softgalli.gurukulshikshalay.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.quesans_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import softgalli.gurukulshikshalay.R
import softgalli.gurukulshikshalay.model.QuesAnsModel
import softgalli.gurukulshikshalay.model.QuizQuestionModel
import java.util.*

class QuesAnsActivity : AppCompatActivity() {
    private var queListCount = 0
    private var queList = ArrayList<QuizQuestionModel>()
    private var quesIndex = 0
    private var selected: IntArray? = null
    private var correctAns: IntArray? = null
    internal var review = false
    internal var select = -1
    internal var questionCount = 1
    var i = 0
    internal var status = true
    private var allqodata: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quesans_activity)

        initWidgit()

    }

    private fun initWidgit() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        queList = intent.getParcelableArrayListExtra<QuizQuestionModel>("list")

        queListCount = queList.size
        selected = IntArray(queListCount)
        Arrays.fill(selected, -1)
        correctAns = IntArray(queListCount)
        Arrays.fill(correctAns, -1)
        showQuestion(0, review)

        clickListener()

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun clickListener() {
        finish.setOnClickListener {
            val results = Intent(this@QuesAnsActivity, ResultActivity::class.java)


            //Calculate Score
            var score = 0
            var notAnswered = 0
            for (i in correctAns!!.indices) {
                if (correctAns!![i] != -1 && correctAns!![i] == selected!![i])
                    score++
            }
            for (i in selected!!.indices) {
                if (selected!![i] == -1)
                    notAnswered++
            }
            results.putExtra("category_id", (queList[0].ques_id))


            results.putExtra("no_of_correct", score.toString())
            results.putExtra("number_of_questions", queListCount.toString())
            results.putExtra("no_of_wrong", (queListCount - score - notAnswered).toString())
            results.putExtra("not_answered", notAnswered.toString())

            startActivity(results)
            finish()
        }


        Next.setOnClickListener {

            quesIndex++;
            questionCount++;
            i++
            if (selected!![quesIndex] == -1) {
                status = true;
                answers.setClickable(true)
                answers.setFocusable(true)
                answers.setEnabled(true)
                for (i in 0 until answers.childCount) {
                    answers.getChildAt(i).setClickable(true)
                    answers.getChildAt(i).setFocusable(true)
                }
            } else
                status = false;

            opt1.setBackgroundResource(R.drawable.circle);
            answer1.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt1.setTextColor(getResources().getColor(R.color.white));
            opt2.setBackgroundResource(R.drawable.circle);
            answer2.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt2.setTextColor(getResources().getColor(R.color.white));
            opt3.setBackgroundResource(R.drawable.circle);
            answer3.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt3.setTextColor(getResources().getColor(R.color.white));
            opt4.setBackgroundResource(R.drawable.circle);
            answer4.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt4.setTextColor(getResources().getColor(R.color.white));

            if (quesIndex >= queListCount + 1)
                quesIndex = queListCount;
            showQuestion(quesIndex, review);
            if (selected!![quesIndex] != -1) {
                if (selected!![quesIndex] == 1) {
                    if (selected!![quesIndex] != correctAns!![quesIndex]) {


                        opt1.setBackgroundResource(R.drawable.wrong_answer_background);
                        opt1.setTextColor(Color.WHITE);


                        if ((correctAns!![quesIndex] == 1)) {
                            opt1.setBackgroundResource(R.drawable.correct_answer_background);

                            opt1.setTextColor(Color.WHITE);

                        }
                        if ((correctAns!![quesIndex] == 2)) {
                            opt2.setBackgroundResource(R.drawable.correct_answer_background);
                            opt2.setTextColor(Color.WHITE);


                        }
                        if ((correctAns!![quesIndex] == 3)) {
                            opt3.setBackgroundResource(R.drawable.correct_answer_background);
                            opt3.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 4)) {
                            opt4.setBackgroundResource(R.drawable.correct_answer_background);
                            opt4.setTextColor(Color.WHITE);
                        }
                    }
                    if (selected!![quesIndex] == correctAns!![quesIndex]) {


                        opt1.setBackgroundResource(R.drawable.correct_answer_background);
                        opt1.setTextColor(Color.WHITE);

                    }

                }


                if (selected!![quesIndex] == 2) {


                    if (selected!![quesIndex] != correctAns!![quesIndex]) {


                        opt2.setBackgroundResource(R.drawable.wrong_answer_background);
                        opt2.setTextColor(Color.WHITE);
                        if ((correctAns!![quesIndex] == 1)) {
                            opt1.setBackgroundResource(R.drawable.correct_answer_background);
                            opt1.setTextColor(Color.WHITE);

                        }
                        if ((correctAns!![quesIndex] == 2)) {
                            opt2.setBackgroundResource(R.drawable.correct_answer_background);
                            opt2.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 3)) {
                            opt3.setBackgroundResource(R.drawable.correct_answer_background);
                            opt3.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 4)) {
                            opt4.setBackgroundResource(R.drawable.correct_answer_background);
                            opt4.setTextColor(Color.WHITE);
                        }
                    }
                    if (selected!![quesIndex] == correctAns!![quesIndex]) {


                        opt2.setBackgroundResource(R.drawable.correct_answer_background);
                        opt2.setTextColor(Color.WHITE);
                    }

                }

                if (selected!![quesIndex] == 3) {
                    if (selected!![quesIndex] != correctAns!![quesIndex]) {


                        opt3.setBackgroundResource(R.drawable.wrong_answer_background);
                        opt3.setTextColor(Color.WHITE);

                        if ((correctAns!![quesIndex] == 1)) {
                            opt1.setBackgroundResource(R.drawable.correct_answer_background);
                            opt1.setTextColor(Color.WHITE);


                        }
                        if ((correctAns!![quesIndex] == 2)) {
                            opt2.setBackgroundResource(R.drawable.correct_answer_background);
                            opt2.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 3)) {
                            opt3.setBackgroundResource(R.drawable.correct_answer_background);
                            opt3.setTextColor(Color.WHITE);

                        }
                        if ((correctAns!![quesIndex] == 4)) {
                            opt4.setBackgroundResource(R.drawable.correct_answer_background);
                            opt4.setTextColor(Color.WHITE);
                        }
                    }
                    if (selected!![quesIndex] == correctAns!![quesIndex]) {


                        opt3.setBackgroundResource(R.drawable.correct_answer_background);
                        opt3.setTextColor(Color.WHITE);

                    }

                }


                if (selected!![quesIndex] == 4) {
                    if (selected!![quesIndex] != correctAns!![quesIndex]) {


                        opt4.setBackgroundResource(R.drawable.wrong_answer_background);
                        opt4.setTextColor(Color.WHITE);
                        if ((correctAns!![quesIndex] == 1)) {
                            opt1.setBackgroundResource(R.drawable.correct_answer_background);
                            opt1.setTextColor(Color.WHITE);


                        }
                        if ((correctAns!![quesIndex] == 2)) {
                            opt2.setBackgroundResource(R.drawable.correct_answer_background);
                            opt2.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 3)) {
                            opt3.setBackgroundResource(R.drawable.correct_answer_background);
                            opt3.setTextColor(Color.WHITE);
                        }
                        if ((correctAns!![quesIndex] == 4)) {
                            opt4.setBackgroundResource(R.drawable.correct_answer_background);
                            opt4.setTextColor(Color.WHITE);
                        }
                    }
                    if (selected!![quesIndex] == correctAns!![quesIndex]) {


                        opt4.setBackgroundResource(R.drawable.correct_answer_background);
                        opt4.setTextColor(Color.WHITE);
                    }

                }

            }


        }

        Prev.setOnClickListener {
            quesIndex--;
            questionCount--;
            i--;
            if (selected!![quesIndex] == -1) {
                status = true;
                answers.setClickable(true);
                answers.setFocusable(true);
                answers.setEnabled(true);
                for (i in 0 until answers.getChildCount()) {
                    answers.getChildAt(i).setClickable(true);
                    answers.getChildAt(i).setFocusable(true);

                }
            } else
                status = false;

            opt1.setBackgroundResource(R.drawable.circle);
            opt1.setTextColor(getResources().getColor(R.color.white));
            answer1.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt2.setBackgroundResource(R.drawable.circle);
            answer2.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt2.setTextColor(getResources().getColor(R.color.white));
            opt3.setBackgroundResource(R.drawable.circle);
            answer3.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt3.setTextColor(getResources().getColor(R.color.white));
            opt4.setBackgroundResource(R.drawable.circle);
            answer4.setTextColor(getResources().getColor(R.color.questionTextDefault));
            opt4.setTextColor(getResources().getColor(R.color.white));

            if (quesIndex < 0)
                quesIndex = 0;


            showQuestion(quesIndex, review);
            if (selected!![quesIndex] != -1) {
                if (selected!![quesIndex] == 1) {

                    showSelectedAnswer(opt1)
                }


                if (selected!![quesIndex] == 2) {
                    showSelectedAnswer(opt2)
                }

                if (selected!![quesIndex] == 3) {
                    showSelectedAnswer(opt3)

                }


                if (selected!![quesIndex] == 4) {
                    showSelectedAnswer(opt4)

                }

            }

        }
    }

    private fun showSelectedAnswer(optText: TextView) {
        if (selected!![quesIndex] != correctAns!![quesIndex]) {

            optText.setBackgroundResource(R.drawable.wrong_answer_background);
            optText.setTextColor(Color.WHITE)

            if ((correctAns!![quesIndex] == 1)) {
                opt1.setBackgroundResource(R.drawable.correct_answer_background);
                opt1.setTextColor(Color.WHITE)
            }
            if ((correctAns!![quesIndex] == 2)) {
                opt2.setBackgroundResource(R.drawable.correct_answer_background);
                opt2.setTextColor(Color.WHITE)
            }
            if ((correctAns!![quesIndex] == 3)) {
                opt3.setBackgroundResource(R.drawable.correct_answer_background);
                opt3.setTextColor(Color.WHITE)
            }
            if ((correctAns!![quesIndex] == 4)) {
                opt4.setBackgroundResource(R.drawable.correct_answer_background);
                opt4.setTextColor(Color.WHITE)
            }
        }
        if (selected!![quesIndex] == correctAns!![quesIndex]) {


            optText.setBackgroundResource(R.drawable.correct_answer_background);
            optText.setTextColor(Color.WHITE);

        }

    }

    private fun showQuestion(qIndex: Int, review: Boolean) {

        try {
            val options = ArrayList<String>()
            if (qIndex == 0) {
                Prev.setVisibility(View.GONE)
                Next.setVisibility(View.VISIBLE)

            } else if (quesIndex + 1 == queListCount) {
                Next.setVisibility(View.GONE)
                Prev.setVisibility(View.VISIBLE)
            } else {
                Next.setVisibility(View.VISIBLE)
                Prev.setVisibility(View.VISIBLE)
            }

            quesCount.setText(resources.getString(R.string.questions) + " " + (qIndex + 1) + "/" + queListCount)
            options.add(queList[i].optionFirst!!)
            options.add(queList[i].optionSecond!!)
            options.add(queList[i].optionThird!!)
            options.add(queList[i].optionFourth!!)

            if (correctAns!![qIndex] == -1) {
                // String correctAnsStr = queList.get(i).getAnswer();
                val answer = queList[qIndex].answer
                var c = 1
                for (corr in options) {
                    if (corr.equals(answer, ignoreCase = true) && c < 5) {
                        correctAns!![qIndex] = c
                    }
                    c++
                }

            }
            val alldata = queList[i].question!!.replace("\\n", "\n").replace("span style=\"color:", "font color=").replace("<br />\r\n", "").replace("span style=\"background-color:", "font color=").replace("\"", "").replace("<p>", "").replace("</p>", "\n").replace("</span>", "</font>")

            question.setText(queList[i].question)
            answer1.setText(queList[i].optionFirst)
            answer2.setText(queList[i].optionSecond)
            answer3.setText(queList[i].optionThird)
            answer4.setText(queList[i].optionFourth)

            setScoreTitle()
            if (quesIndex + 1 == queListCount) {
                Next.setClickable(false)
                Next.setFocusable(false)
            }


            if (quesIndex == 0) {
                Prev.setEnabled(false)
            }
            if (quesIndex > 0) {
                Prev.setEnabled(true)
            }
            if (quesIndex + 1 < queListCount) {

                Next.setClickable(true)
                Next.setFocusable(true)
            }

        } catch (e: Exception) {
            Log.e(this.javaClass.toString(), e.message, e.cause)
        }

        opt1.setOnClickListener {
            optionClicked(1, opt1)
        }
        answer1.setOnClickListener {
            optionClicked(1, opt1)
        }
        opt2.setOnClickListener {
            optionClicked(2, opt2)
        }
        answer2.setOnClickListener {
            optionClicked(2, opt2)
        }
        opt3.setOnClickListener {
            optionClicked(3, opt3)
        }
        answer3.setOnClickListener {
            optionClicked(3, opt3)
        }
        opt4.setOnClickListener {
            optionClicked(4, opt4)
        }
        answer4.setOnClickListener {
            optionClicked(4, opt4)
        }

    }

    private fun optionClicked(position: Int, optText: TextView) {
        if (status == false) {

            answers.setClickable(false)
            answers.setFocusable(false)
            answers.setEnabled(false)

            for (i in 0 until answers.getChildCount()) {
                answers.getChildAt(i).setClickable(false)
                answers.getChildAt(i).setFocusable(false)
            }

        } else {
            selected!![quesIndex] = position
            status = false
            if (select == correctAns!![quesIndex]) {

                optText.setBackgroundResource(R.drawable.correct_answer_background)
                optText.setTextColor(Color.WHITE)

            }
            if (select != correctAns!![quesIndex]) {

                optText.setBackgroundResource(R.drawable.wrong_answer_background)
                optText.setTextColor(Color.WHITE)
                if (correctAns!![quesIndex] == 1) {
                    opt1.setBackgroundResource(R.drawable.correct_answer_background)
                    opt1.setTextColor(Color.WHITE)


                }
                if (correctAns!![quesIndex] == 2) {
                    opt2.setBackgroundResource(R.drawable.correct_answer_background)

                    opt2.setTextColor(Color.WHITE)

                }
                if (correctAns!![quesIndex] == 3) {
                    opt3.setBackgroundResource(R.drawable.correct_answer_background)
                    opt3.setTextColor(Color.WHITE)
                }
                if (correctAns!![quesIndex] == 4) {
                    opt4.setBackgroundResource(R.drawable.correct_answer_background)
                    opt4.setTextColor(Color.WHITE)
                }
            }
        }
    }


    private fun setScoreTitle() {
        this.title = R.string.SciQuiz3.toString() + "     " + (quesIndex + 1) + "/" + queListCount
    }
}