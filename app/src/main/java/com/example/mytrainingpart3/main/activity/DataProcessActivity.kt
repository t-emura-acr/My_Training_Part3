package com.example.mytrainingpart3.main.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingpart3.R
import com.example.mytrainingpart3.main.data.DataBaseHelper
import java.lang.Exception
import java.lang.NumberFormatException

class DataProcessActivity : AppCompatActivity() {

    //ID
    private lateinit var mTextId: EditText

    //タイトル
    private lateinit var mTextTitle: EditText

    //内容
    private lateinit var mTextDescription: EditText

    //テーブル名
    private val tableName = "word_data"

    //空白
    private val strEmpty = ""

    private val mDBHelper : DataBaseHelper = DataBaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_process)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()

        //DataBaseHelperオブジェクト解放
        mDBHelper.close()
    }

    private fun initView(){

        //各項目取得

        mTextId = findViewById(R.id.db_id)

        mTextTitle = findViewById(R.id.db_title)

        mTextDescription = findViewById(R.id.db_discription)

        //検索ボタン

        val searchbtn: Button = findViewById(R.id.button_db_search)

        searchbtn.setOnClickListener {
            Log.d("検索",searchbtn.text.toString())
            selectData(mTextId.text.toString())
        }

        //登録ボタン

        val regstbtn: Button = findViewById(R.id.button_db_reg)

        regstbtn.setOnClickListener {
            Log.d("登録",regstbtn.text.toString())
            registData(mTextTitle.text.toString(), mTextDescription.text.toString())
        }

        //更新ボタン

        val updatebtn: Button = findViewById(R.id.button_db_update)

        updatebtn.setOnClickListener {
            Log.d("更新",updatebtn.text.toString())
            updateData(mTextId.text.toString(),mTextTitle.text.toString(), mTextDescription.text.toString())
        }

        //削除ボタン

        val deletebtn: Button = findViewById(R.id.button_db_delete)

        deletebtn.setOnClickListener {
            Log.d("削除",deletebtn.text.toString())
            deleteData(mTextId.text.toString())
        }
    }

    //処理部分

    //検索処理
    private fun selectData(id:String){

        //idがブランクの場合処理しない
        if(id.isEmpty()){
            return
        }

        try{
            //IDを数値で取り込み
            val id = id.toInt()

            //読み取り専用DB取得
            val database = mDBHelper.readableDatabase

            //命令文
            val sql = "select * from $tableName where id = $id"

            //実行、cursorオブジェクトで値受け取り
            val cursor = database.rawQuery(sql,null)

            //レコード表示(存在する場合)
            if(cursor.count > 0) {

                //最初のレコードへ移動
                cursor.moveToFirst()

                //レコードから値を取り出し画面上にセットする
                mTextId.setText(cursor.getInt(0).toString())
                mTextTitle.setText(cursor.getString(1))
                mTextDescription.setText(cursor.getString(2))
            }
        } catch (e: NumberFormatException){

            //数値変換できない場合例外設定
            e.printStackTrace()
        } catch (e: Exception){

            //検索処理の例外設定
            e.printStackTrace()
        }
    }

    //登録処理
    private fun registData(title: String, description:String){

        //タイトルもしくは内容がブランクの場合処理しない
        if(title.isEmpty() || description.isEmpty()){
            return
        }

        try{
            //書き込み可能DB取得
            val database = mDBHelper.writableDatabase

            //登録する値のセット
            val values = ContentValues()
            values.put("title", title)
            values.put("description",description)

            //命令文
            database.insertOrThrow(tableName,null,values)

            //画面の入力情報クリア
            clearDisplay()
        } catch (e: Exception){

            e.printStackTrace()
        }
    }

    //更新処理
    private fun updateData(id:String,title: String, description:String){

        //正規表現による数値判定
        //0-9の数字が１回以上か判定（数字以外ではないか）
        val regex = Regex("[0-9]+")

        if(id.isEmpty()){
            Toast.makeText(applicationContext, "IDがブランクです", Toast.LENGTH_LONG).show()
            return
        }
        if(id.length > 5){
            Toast.makeText(applicationContext, "ID桁数エラー", Toast.LENGTH_LONG).show()
            return
        }
        if(!regex.matches(id)){
            Toast.makeText(applicationContext, "ID体系エラー", Toast.LENGTH_LONG).show()
            return
        }

        //IDおよびタイトルもしくは内容がブランクの場合処理しない
        if(id.isEmpty() || title.isEmpty() || description.isEmpty()){
            return
        }

        try{
            //書き込み可能DB取得
            val database = mDBHelper.writableDatabase

            //登録する値のセット
            val values = ContentValues()
            values.put("title", title)
            values.put("description",description)

            //where区の構文設定
            val whereClauses = "id = ?"

            //構文の？に入る値を設定
            val whereArgs = arrayOf(id)

            //命令文 テーブル名、値、検索条件、？の値
            database.update(tableName,values,whereClauses,whereArgs)

            //画面の入力情報クリア
            clearDisplay()
        } catch (e: Exception){

            e.printStackTrace()
        }
    }

    //削除処理
    private fun deleteData(id:String){

        //IDがブランクの場合処理しない
        if(id.isEmpty()){
            return
        }

        try{
            //書き込み可能DB取得
            val database = mDBHelper.writableDatabase

            //where区の構文設定
            val whereClauses = "id = ?"

            //構文の？に入る値を設定
            val whereArgs = arrayOf(id)

            //命令文 テーブル名、検索条件、？の値
            database.delete(tableName,whereClauses,whereArgs)

            //画面の入力情報クリア
            clearDisplay()
        } catch (e: Exception){

            e.printStackTrace()
        }
    }

    //クリア処理
    private fun clearDisplay(){

        //画面上の値をブランクにする
        mTextId.setText(strEmpty)
        mTextTitle.setText(strEmpty)
        mTextDescription.setText(strEmpty)

    }



}