package com.greedy.moviest.login


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.moviest.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        /* 가입 버튼 클릭 시 firebase로 계정 등록 요청 */
        binding.btnJoin.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            /* 여러 유효성 검사 추가할 수 있음 */
            if(email.isNotEmpty() && password.isNotEmpty()){
                createAccount(email, password)
            }else{
                Toast.makeText(this, "email과 password를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        /* 취소 버튼 클릭 시 JoinActivity 종료(MainActivity로 돌아감) */
        binding.btnCancle.setOnClickListener{ finish() }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공 시
                    Toast.makeText(this,"회원 가입 완료", Toast.LENGTH_SHORT).show()
                    finish() // 회원 가입창 종료하고 메인-로그인창으로 돌아감
                } else {
                    // 회원 가입 실패 시
                    Toast.makeText(this, "회원 가입 실패", Toast.LENGTH_SHORT).show()

                }
            }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        // 로그인 되어있는지 상태를 확인하여 로그인 되어 있다면
        // 회원가입 창 액티비티 종료
        val currentUser = auth.currentUser
        if(currentUser != null){
            finish()
        }
    }
}