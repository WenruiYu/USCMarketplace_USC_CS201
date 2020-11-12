<template>
    <div class="container">
        <div style="width: 100%; margin-top: 25px;">
            <el-tabs tabPosition="left" v-model="activeName">
                <el-tab-pane label="Account Info" name="first">
                    <div class="form-box">
                        <el-form :model="registerForm" ref="registerForm" label-width="100px" class="ms-content"  style="width: 50%; min-width: 400px">
                            <el-form-item prop="username" label="Username: ">
                                <el-input disabled v-model="registerForm.username" placeholder="Username">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="realName" label="Real Name: ">
                                <el-input v-model="registerForm.realName" placeholder="Real Name">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="email" label="Email: ">
                                <el-input v-model="registerForm.email" placeholder="Email">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="mobile" label="Mobile: ">
                                <el-input v-model="registerForm.mobile" placeholder="Mobile">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="uscId" label="USC ID: ">
                                <el-input v-model="registerForm.uscId" placeholder="USC ID">
                                </el-input>
                            </el-form-item>
                            <div>
                                <el-button type="primary" @click="onSubmit">SAVE INFO</el-button>
                            </div>
                        </el-form>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="Modify Password" name="second">
                    <div class="form-box">
                        <el-form label-width="150px" style="width: 50%; min-width: 400px">
                            <el-form-item label="Current Password">
                                <el-input type="password" placeholder="Input current password" v-model="pre"></el-input>
                            </el-form-item>
                            <el-form-item label="New Password">
                                <el-input type="password" placeholder="Input new password" v-model="cur"></el-input>
                            </el-form-item>
                            <el-form-item label="New Password Again">
                                <el-input type="password" placeholder="Input new password again" v-model="dupCur"></el-input>
                            </el-form-item>
                            <div>
                                <el-button type="primary" @click="onModifyPassword">Confirm modify</el-button>
                            </div>
                        </el-form>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
  import {KEY_STORE} from "../common/common";
  import {get, post, URL} from "../api";

  export default {
    name: "Account",
    data() {
      return {
        registerForm: JSON.parse(localStorage.getItem(KEY_STORE.INFO)),
        pre: '',
        cur: '',
        dupCur: '',
        activeName: 'first',
      }
    },
    mounted() {
    },
    computed: {
      info() {
        return JSON.parse(localStorage.getItem(KEY_STORE.INFO));
      }
    },
    methods: {
      // 保存编辑
      onSubmit() {
        post(URL.Account.modifyInfo, this.registerForm).then(data => {
          this.$message.success(data.data);
          localStorage.setItem(KEY_STORE.INFO, JSON.stringify(this.registerForm));
        });
      },
      onModifyPassword() {
        if (this.cur !== this.dupCur) {
          this.$message.error("Password not matched。");
          return
        }
        post(URL.Account.changePassword, {
          pre: this.pre,
          cur: this.cur
        }).then(res => {
          this.$message.success(res.data)
        })
      }
    }
  }
</script>

<style scoped>

</style>
