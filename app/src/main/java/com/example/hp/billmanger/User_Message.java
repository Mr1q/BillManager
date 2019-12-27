package com.example.hp.billmanger;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.Calendar;


import com.example.hp.billmanger.Activity.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class User_Message extends  BaseActivity implements  View.OnClickListener{
    private EditText name;
    private EditText mail;
    private EditText birthday;
    private Button modify;
    private ImageView return1;
    private EditText address;
    private RadioButton male,femle;
    private  Button save;
    private CircleImageView userimage;
    // 拍照回传码
    public final static int CAMERA_REQUEST_CODE = 0;
    // 相册选择回传吗
    public final static int GALLERY_REQUEST_CODE = 1;
    // 拍照的照片的存储位置
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    private Uri imageUri;
    private Uri destinationUri;

        //日期选择器
    @BindView(R.id.riqi)
    Button riqi;

private Boolean X=false;

private  String Name;
private  String Birthday;
private  String Address;
private  Person bmobUser;
private   Person person;
private  Uri croppedUri;



    private BmobFile image;
    private boolean bol=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_message);
        ButterKnife.bind(this);



        bmobUser = BmobUser.getCurrentUser(Person.class);
        person=new Person();
        init();
        modify=(Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                X=true;
                name.setEnabled(true);
                mail.setEnabled(true);
                birthday.setEnabled(true);
                male.setClickable(true);
                femle.setClickable(true);
                address.setEnabled(true);

            }
        });
       return1=(ImageView)findViewById(R.id.return_1);
       return1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(User_Message.this, MainActivity.class);
               startActivity(intent);
               User_Message.this.finish();
           }
       });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(false);
                mail.setEnabled(false);
                birthday.setEnabled(false);
                address.setEnabled(false);
                if (male.isChecked()) {
                    male.setClickable(false);
                    femle.setClickable(false);
                } else if (femle.isChecked()) {
                    male.setClickable(false);
                    femle.setClickable(false);
                }

                //判断是否要保存数据
                if (X == true||bol==true) {

                    //判断是否进行图片选择
                    if(bol==true) {
                        final File file = new File(UriHelper.getPath(User_Message.this, croppedUri));
                        final BmobFile File1 = new BmobFile(file);
                        File1.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    person.setImage(File1);
                                  //  判断数据是否修改，修改进行更新
                                    if (!(male.isChecked()&&bmobUser.getSex().equals("男"))) {
                                        person.setSex("男");
                                    } else if (!(femle.isChecked()&&bmobUser.getSex().equals("女"))){
                                        person.setSex("女");
                                    }
                                    if(!(birthday.getText().toString().equals(bmobUser.getBirthday().toString())))
                                    {
                                        person.setBirthday(birthday.getText().toString());
                                    }
                                    if(!(name.getText().toString().equals(bmobUser.getUsername().toString())))
                                    {
                                        person.setUsername(name.getText().toString());
                                    }
                                    if(!(mail.getText().toString().equals(bmobUser.getEmail().toString())))
                                    {
                                        person.setEmail(mail.getText().toString());
                                    }
                                    if(!(address.getText().toString().equals(bmobUser.getAddress().toString())))
                                    {
                                        person.setAddress(address.getText().toString());
                                    }
                                    person.update(bmobUser.getObjectId(),new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(User_Message.this, "保存成功", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(User_Message.this, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        bol=false;
                    }
                    else
                        {
                            if (!(male.isChecked()&&bmobUser.getSex().equals("男"))) {
                                person.setSex("男");
                            } else if (!(femle.isChecked()&&bmobUser.getSex().equals("女"))){
                                person.setSex("女");
                            }
                            if(!(birthday.getText().toString().equals(bmobUser.getBirthday().toString())))
                            {
                                person.setBirthday(birthday.getText().toString());
                            }
                            if(!(name.getText().toString().equals(bmobUser.getUsername().toString())))
                            {
                                person.setUsername(name.getText().toString());
                            }
                            if(!(mail.getText().toString().equals(bmobUser.getEmail().toString())))
                            {
                                person.setEmail(mail.getText().toString());
                            }
                            if(!(address.getText().toString().equals(bmobUser.getAddress().toString())))
                            {
                                person.setAddress(address.getText().toString());
                            }
                   person.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                                    if (e==null)
                                    {
                                        Toast.makeText(User_Message.this,"保存成功",Toast.LENGTH_SHORT).show();
                                     }
                                     else {

                                        Toast.makeText(User_Message.this,"保存失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                          });
                      }
                }
                else
                {
                    Toast.makeText(User_Message.this, "没有修改", Toast.LENGTH_SHORT).show();
                }
                X = false;
            }
        });
        }

                private void init () {
                    userimage = (CircleImageView) findViewById(R.id.message_image);
                    address = (EditText) findViewById(R.id.address);
                    name = (EditText) findViewById(R.id.username2);
                    mail = (EditText) findViewById(R.id.mail_1);
                    birthday = (EditText) findViewById(R.id.birthday_1);
                    male = (RadioButton) findViewById(R.id.male);
                    femle = (RadioButton) findViewById(R.id.femle);
                    save = (Button) findViewById(R.id.save);
                    femle.setClickable(false);
                    male.setClickable(false);
                    name.setEnabled(false);
                    mail.setEnabled(false);
                    birthday.setEnabled(false);
                    address.setEnabled(false);
                    //初始化信息数据
                    exit();
                    mail.setText(bmobUser.getEmail());
                    userimage.setOnClickListener(this);
                    if (bmobUser.getImage() != null) {
                        String uri = bmobUser.getImage().getUrl();
                        Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.ccc).diskCacheStrategy(DiskCacheStrategy.NONE).into(userimage);

                    }
                    riqi.setOnClickListener(this);


                }

                private void exit () {

                    if (!bmobUser.getAddress().equals("")) {
                        address.setText(bmobUser.getAddress());
                    }
                    if (!bmobUser.getBirthday().equals("")) {
                        birthday.setText(bmobUser.getBirthday());
                    }
                    if (!bmobUser.getUsername().equals("")) {
                        name.setText(bmobUser.getUsername());
                    }
                    if (bmobUser.getSex().equals("男")) {
                        male.setChecked(true);

                    } else if (bmobUser.getSex().equals("女")) {
                        femle.setChecked(true);
                    }
                    if (!bmobUser.getEmail().equals("")) {
                        mail.setText(bmobUser.getEmail());
                    }
                }


                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick (View v){

                    switch (v.getId()) {
                        case R.id.message_image:
                            bol = true;
                            Intent photo = new Intent(Intent.ACTION_PICK, null);
                            photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(photo, GALLERY_REQUEST_CODE);
                            break;
                        case R.id.riqi:
                            Calendar calendar = Calendar.getInstance();
                            new DatePickerDialog(User_Message.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                                    Toast.makeText(User_Message.this, text, Toast.LENGTH_SHORT).show();
                                    birthday.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                            break;
                    }

                }

                @Override
                protected void onActivityResult ( int requestCode, int resultCode, Intent data){
                    if (resultCode == User_Message.RESULT_OK) {
                        switch (requestCode) {
                            case GALLERY_REQUEST_CODE: {
                                try {
                                    //该uri是上一个Activity返回的
                                    imageUri = data.getData();
                                    if (imageUri != null) {
                                        destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpg"));
                                        UCrop.of(imageUri, destinationUri).withAspectRatio(16, 9).withMaxResultSize(300, 300).start(this);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            break;

                            case UCrop.REQUEST_CROP: {
                                croppedUri = UCrop.getOutput(data);
                                try {
                                    if (croppedUri != null) {
                                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(croppedUri));
                                        userimage.setImageBitmap(bit);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }

                        }
                    }
                    super.onActivityResult(requestCode, resultCode, data);

                }

                private void startUCrop () {
                    //裁剪后保存到文件中

                    Uri destionUri = Uri.fromFile(new File(getCacheDir(), "myCroppedImage.jpg"));
                    UCrop uCrop = UCrop.of(imageUri, destionUri);
                    UCrop.Options options = new UCrop.Options();
                    //设置裁剪图片可操作的手势
                    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                    options.setMaxBitmapSize(100);
                    options.setShowCropFrame(false);
                    options.setFreeStyleCropEnabled(true);  //是否能调整裁剪框
                    uCrop.withOptions(options);
                    uCrop.start(this);
                }


            }
