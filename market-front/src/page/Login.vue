<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">Welcome to USC Marketplace</div>
            <el-form :model="passwordParam" :rules="rules" ref="loginPass" label-width="0px" class="ms-content"
                     v-if="loginType === 'password'">
                <el-form-item prop="username">
                    <el-input v-model="passwordParam.username" placeholder="Username">
                        <el-button slot="prepend" icon="el-icon-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                            type="password"
                            placeholder="Password"
                            v-model="passwordParam.password"
                    >
                        <el-button slot="prepend" icon="el-icon-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">Login</el-button>
                </div>
                <div class="login-btn">
                    <el-button type="danger" @click="loginAsGuess()">Login As Guess</el-button>
                </div>
                <div>
                    <el-button class="login-tips" type="text" @click="loginType = 'register'">Sign Up ></el-button>
                </div>
            </el-form>

            <el-form :model="registerForm" ref="registerForm" label-width="0px" class="ms-content"
                     v-else>
                <el-form-item prop="username">
                    <el-input v-model="registerForm.username" placeholder="Username">
                        <el-button slot="prepend" icon="el-icon-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="realName">
                    <el-input v-model="registerForm.realName" placeholder="Real Name">
                        <el-button slot="prepend" icon="el-icon-user"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="Password">
                        <el-button slot="prepend" icon="el-icon-lock"></el-button>
                    </el-input>
                </el-form-item>

                <el-form-item prop="email">
                    <el-input v-model="registerForm.email" placeholder="Email">
                        <el-button slot="prepend" icon="el-icon-message"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="mobile">
                    <el-input v-model="registerForm.mobile" placeholder="Mobile">
                        <el-button slot="prepend" icon="el-icon-phone-outline"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="uscId">
                    <el-input v-model="registerForm.uscId" placeholder="USC ID">
                        <el-button slot="prepend" icon="el-icon-school"></el-button>
                    </el-input>
                </el-form-item>

                <div class="login-btn">
                    <el-button type="primary" @click="onRegister()">Register</el-button>
                </div>
                <div>
                    <el-button class="login-tips" type="text" @click="loginType = 'password'">Return To Login >
                    </el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
  import {URL, get, post} from '../api';
  import {KEY_STORE} from '@/common/common';

  export default {
    data: function () {
      return {
        passwordParam: {
          username: localStorage.getItem(KEY_STORE.USERNAME) ? localStorage.getItem(KEY_STORE.USERNAME) : '',
          password: ''
        },
        rules: {
          username: [{required: true, message: '* required', trigger: 'change'}],
          password: [{required: true, message: '* required'}]
        },
        loginType: 'password',
        waiting: false,
        waitingTime: 60,
        waitingTimer: undefined,
        registerForm: {
          username: '',
          password: '',
          realName: '',
          email: '',
          uscId: '',
          mobile: ''
        },
        registerRule: {
          username: [{required: true, message: '* required'}],
          password: [{required: true, message: '* required'}],
          email: [{required: true, message: '* required'}],
          realName: [{required: true, message: '* required'}],
          uscId: [{required: true, message: '* required'}],
          mobile: [{required: true, message: '* required'}],
        },
      };
    },
    methods: {
      loginAsGuess() {
        this.$router.push({path: '/'})
      },
      submitForm() {
        let comp = this.$refs.loginPass;
        let param = this.passwordParam;
        localStorage.setItem(KEY_STORE.USERNAME, param.username)
        comp.validate(valid => {
          if (valid) {
            post(URL.Auth.login, param).then(
              data => {
                let token = data.data;
                console.log(data);
                localStorage.setItem(KEY_STORE.TOKEN, token);
                // update local info
                get(URL.Account.getInfo).then(
                  data => {
                    console.log(data);
                    localStorage.setItem(KEY_STORE.INFO, JSON.stringify(data.data));
                    this.$router.push('/');
                  }
                );
              }
            ).catch(err => {
            });
          } else {
            return false;
          }
        });
      },
      onRegister() {
        this.$refs.registerForm.validate(
          valid => {
            if (valid) {
              post(URL.Auth.register, this.registerForm).then(
                data => {
                  this.$message.success(data.data)
                }
              )
            } else {
              return false;
            }
          }
        )
      }
    }
  };
</script>

<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
        background-image: url(../assets/img/login-bg.jpg);
        background-size: 100%;
    }

    .ms-title {
        width: 100%;
        line-height: 50px;
        text-align: center;
        font-size: 20px;
        color: #000;
        border-bottom: 1px solid #ddd;
    }

    .ms-login {
        position: absolute;
        left: 50%;
        top: 25%;
        width: 350px;
        margin: -100px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255, 255, 255, .3);
        overflow: hidden;
    }

    .ms-content {
        padding: 30px 30px;
    }

    .login-btn {
        text-align: center;
    }

    .login-btn button {
        width: 100%;
        height: 36px;
        margin-bottom: 10px;
    }

    .login-tips {
        font-size: 12px;
        color: #111;
        float: right;
        margin-bottom: 10px;
        border-bottom: 1px solid black;
        border-radius: 0;
    }
</style>
