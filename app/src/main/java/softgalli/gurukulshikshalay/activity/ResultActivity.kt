package softgalli.gurukulshikshalay.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.results_activity.*
import softgalli.gurukulshikshalay.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results_activity)

        setValue()

        tryAgain.setOnClickListener {
            finish()
        }

        home.setOnClickListener {
            startActivity(Intent(this, QuizQuesCategoryActivity::class.java))
            finish()
        }
    }

    private fun setValue() {
        score.setText(getIntent().getStringExtra("no_of_correct") + " /" + getIntent().getStringExtra("number_of_questions"))
        no_of_correct.setText(getIntent().getStringExtra("no_of_correct"))
        no_of_wrong.setText(getIntent().getStringExtra("no_of_wrong"))
        not_answered.setText(getIntent().getStringExtra("not_answered"))
    }
}