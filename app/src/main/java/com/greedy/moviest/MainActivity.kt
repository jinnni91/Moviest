package com.greedy.moviest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.greedy.moviest.databinding.ActivityMainBinding
import com.greedy.moviest.login.JoinActivity
import com.greedy.moviest.login.ResultActivity


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    //private var auth : FirebaseAuth? = null
    private var googleSignInClient : GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        // Initialize Firebase Auth
        //auth = Firebase.auth

        /* 회원가입 버튼 클릭 시 동작하는 이벤트 */
        binding.join.setOnClickListener{
            startActivity(Intent(this, JoinActivity::class.java))
        }

        /* 로그인 버튼 클릭 시 동작하는 이벤트 */
        binding.btnLogin.setOnClickListener{

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            /* 여러 유효성 검사 추가할 수 있음 */
            if(email.isNotEmpty() && password.isNotEmpty()){
                signIn(email, password)
            }else{
                Toast.makeText(this, "email과 password를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


        // 구글 로그인 버튼
       binding.googleButton.setOnClickListener { googleLogin() }


        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    moveResultPage()
                } else {
                    Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

    }

    // 구글 로그인 함수
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    // 아이디, 비밀번호 맞을 때
                    moveResultPage()
                }else{
                    // 틀렸을 때
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!
            // 구글API가 넘겨주는 값 받아옴

            if(result.isSuccess) {
                var accout = result.signInAccount
                firebaseAuthWithGoogle(accout)
                Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
            }
        }
    }



    /* 로그인 된 상태라면 로그인 이후 화면인 ResultActivity로 이동 */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            moveResultPage()
        }
    }

    /* 로그인이 이미 되어 있는 상태 또는 로그인에 성공하는 상태에 호출할 메소드 */
    fun moveResultPage() {
        startActivity(Intent(this, ResultActivity::class.java))
        finish()

    }
}