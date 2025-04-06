<script setup>
import { getUserInfoAPI, getVerifyAPI, loginAPI, registerAPI } from '@/api/user';
import { useUserStore } from '@/stores/userStore';
import { ElNotification } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
  handleClose: Function
})
const userStore = useUserStore();
const regEmailCheck = ref(true);
const regPasswordCheck = ref(true);
const regVerifyCheck = ref(true);
const emailCheck = ref(true);
const passwordCheck = ref(true);

const emailErrorMsg = ref('');
const passwordErrorMsg = ref('');
const regEmailErrorMsg = ref('');
const regPasswordErrorMsg = ref('');
const regVerifyErrorMsg = ref('');

const router = useRouter();

const login = ref({
  email: '',
  password: ''
});
const register = ref({
  email: '',
  password: '',
  verify: ''
});
const isLogin = ref(true);//switch控制状态
const changState = () => {
  isLogin.value = !isLogin.value
  setTimeout(() => {
    regEmailCheck.value = true;
    regPasswordCheck.value = true;
    regVerifyCheck.value = true;
    emailCheck.value = true;
    passwordCheck.value = true;
    login.value.email = '';
    login.value.password = '';
    register.value.email = '';
    register.value.password = '';
    register.value.verify = '';
  }, 600)
}

const checkEmail = () => {
  if (!login.value.email) {
    emailCheck.value = false;
    emailErrorMsg.value = '邮箱不能为空!';
    return;
  }
  login.value.email = login.value.email.trim();
  if (login.value.email === '') {
    emailCheck.value = false;
    emailErrorMsg.value = '邮箱不能为空!';
    return;
  }
  // 邮箱格式校验
  if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(login.value.email)) {
    emailCheck.value = false;
    emailErrorMsg.value = '邮箱格式不正确!';
    return;
  }
  emailCheck.value = true;
}
const checkRegEmail = () => {
  if (!register.value.email) {
    regEmailCheck.value = false;
    regEmailErrorMsg.value = '邮箱不能为空!';
    return;
  }
  register.value.email = register.value.email.trim();
  if (register.value.email === '') {
    regEmailCheck.value = false;
    regEmailErrorMsg.value = '邮箱不能为空!';
    return;
  }
  // 邮箱格式校验
  if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(register.value.email)) {
    regEmailCheck.value = false;
    regEmailErrorMsg.value = '邮箱格式不正确!';
    return;
  }
  regEmailCheck.value = true;
}

const checkPassword = () => {
  if (!login.value.password) {
    passwordCheck.value = false;
    passwordErrorMsg.value = '密码不能为空!';
    return;
  }
  login.value.password = login.value.password.trim();
  if (login.value.password === '') {
    passwordCheck.value = false;
    passwordErrorMsg.value = '密码不能为空!';
    return;
  }
  passwordCheck.value = true;
}

const checkRegPassword = () => {
  if (!register.value.password) {
    regPasswordCheck.value = false;
    regPasswordErrorMsg.value = '密码不能为空!';
    return;
  }
  register.value.password = register.value.password.trim();
  if (register.value.password === '') {
    regPasswordCheck.value = false;
    regPasswordErrorMsg.value = '密码不能为空!';
    return;
  }
  if (!/^.{6,16}$/.test(register.value.password)) {
    regPasswordCheck.value = false;
    regPasswordErrorMsg.value = '密码长度应在6~16之间!';
    return;
  }
  regPasswordCheck.value = true;
}
const checkRegVerify = () => {
  if (!register.value.verify) {
    regVerifyCheck.value = false;
    regVerifyErrorMsg.value = '验证码不能为空!';
    return;
  }
  regVerifyCheck.value = true;
}

const getVerify = () => {
  checkRegEmail();
  if (!regEmailCheck.value) return;
  getVerifyAPI(register.value.email);
  ElNotification.success('发送成功');
}

const signin = async () => {
  checkEmail();
  checkPassword();
  if (!emailCheck.value || !passwordCheck.value) return;

  const res = await loginAPI(login.value);
  if (res.code != 200) {
    ElNotification.error({
      title: '登录失败',
      message: res.msg,
    });
    return;
  }
  ElNotification.success('登录成功');
  //用户就去home 管理员就去admin 存入token
  userStore.jwt = res.data.jwt;
  props.handleClose();
  if (res.data.userAuth === 'superAdmin') router.push({ name: 'DashBoard' });
  const userInfoRes = await getUserInfoAPI();
  Object.assign(userStore.userInfo, userInfoRes.data);
}
const signup = async () => {
  checkRegEmail();
  checkRegPassword();
  checkRegVerify();
  if (!regEmailCheck.value || !regPasswordCheck.value || !regVerifyCheck.value) return;
  const res = await registerAPI(register.value);
  if (res.code != 200) {
    ElNotification.error({
      title: '注册失败',
      message: res.msg,
    });
    return;
  }
  ElNotification.success('注册成功');
  changState();
}


</script>

<template>
  <div class="login-container">
    <div class="shell">
      <div class="register box  "
        :style="{ left: isLogin ? '400px' : '0px', opacity: isLogin ? 0 : 1, 'z-index': isLogin ? 0 : 100 }">
        <form action="" class="register-form" @submit.prevent>
          <span>创建账号</span>
          <div class="input-container">
            <input type="text" placeholder="email" v-model="register.email" @blur="checkRegEmail()">
            <div v-if="!regEmailCheck" class="error-msg">
              <span>{{ regEmailErrorMsg }} </span>
            </div>
          </div>
          <div class="input-container">
            <input type="password" placeholder="password" autocomplete="current-password" v-model="register.password"
              @blur="checkRegPassword()">
            <div v-if="!regPasswordCheck" class="error-msg">
              <span>{{ regPasswordErrorMsg }}</span>
            </div>
          </div>
          <div class="input-container verify">
            <input class="verify-input" type="text" placeholder="verify" v-model="register.verify"
              @blur="checkRegVerify()">
            <button class="verify-button" @click="getVerify">
              获取验证码
            </button>
            <div v-if="!regVerifyCheck" class="error-msg">
              <span>{{ regVerifyErrorMsg }}</span>
            </div>
          </div>
          <button @click="signup">SIGN UP</button>
        </form>
      </div>
      <div class="login box"
        :style="{ left: isLogin ? '400px' : '0px', opacity: isLogin ? 1 : 0, 'z-index': isLogin ? 100 : 0 }">
        <form action="" class="login-form" @submit.prevent>
          <span>登录账号</span>
          <div class="input-container">
            <input type="text" placeholder="email" v-model="login.email" @blur="checkEmail()">
            <div v-if="!emailCheck" class="error-msg">
              <span>{{ emailErrorMsg }}</span>
            </div>
          </div>
          <div class="input-container">
            <input type="password" placeholder="password" autocomplete="current-password" v-model="login.password"
              @blur="checkPassword()">
            <div v-if="!passwordCheck" class="error-msg">
              <span>{{ passwordErrorMsg }}</span>
            </div>
          </div>
          <button @click="signin">SIGN IN</button>
        </form>
      </div>
      <div class="switch" :style="{ left: isLogin ? '0px' : '600px' }">
        <div class="circle"></div>
        <div class="switch-to-register switch-container " :style="{ opacity: isLogin ? 0 : 1 }">
          <span>Welcome Login!</span>
          <p>已经有账号了嘛,去登录探索吧!</p>
          <button @click="changState">SIGN IN</button>
        </div>
        <div class="switch-to-login switch-container" :style="{ opacity: isLogin ? 1 : 0 }">
          <span>Welcome Register!</span>
          <p>还没有账号? 速速注册吧~</p>
          <button @click="changState">SIGN UP</button>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped lang="scss">
@keyframes moveForm {
  from {
    left: 400px;
  }

  to {
    left: 0;
  }

}

@keyframes moveSwitch {
  from {
    left: 0;
  }

  to {
    left: 600px;
  }
}


.login-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;

  a {
    font-size: 16px;
    margin-top: 10px;
    margin-left: 270px;
    font-weight: 700;
    color: #181818;
    letter-spacing: 4px;
  }

  button {
    width: 180px;
    height: 50px;
    border-radius: 25px;
    margin-top: 50px;
    font-weight: 700;
    font-size: 14px;
    letter-spacing: 1.15px;
    background-color: #4B70E2;
    color: #f9f9f9;
    box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;
    border: none;
    outline: none;
    pointer-events: auto;

    &:hover {
      cursor: pointer;
    }
  }

  .shell {
    position: relative;
    width: 1000px;
    height: 600px;
    padding: 25px;
    background-color: #ecf0f3;
    box-shadow: 3px 6px 10px rgba(152, 152, 152, 0.7);
    border-radius: 12px;
    overflow: hidden;

    .box {
      display: flex;
      justify-content: center;
      align-items: center;
      position: absolute;
      top: 0;
      width: 600px;
      height: 100%;
      padding: 25px;
      background-color: #ecf0f3;
      transition:
        left 1.25s cubic-bezier(0.68, -0.55, 0.265, 1.55),
        opacity 0.01s ease 0.58s;

      .input-container {
        position: relative;
        margin-bottom: 23px;

        .error-msg {
          margin-top: 2px;
          margin-left: 8px;
          position: absolute;

          span {
            padding: 2px 0;
            font-size: 14px;
            line-height: 0;
            color: #dd2727;
            letter-spacing: 2px;
          }
        }
      }

      .verify {
        .verify-button {
          position: absolute;
          top: 2px;
          right: 0;
          width: 100px;
          height: 36px;
          border-radius: 8px;
          margin: 0;
          background-color: rgba($color: #000000, $alpha: 0.2);
        }
      }

      form {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 100%;
        height: 100%;
        animation: moveForm 1.25s;

        span {
          font-size: 34px;
          font-weight: 700;
          line-height: 3;
          color: #181818;
          letter-spacing: 10px;
        }

        input {
          width: 350px;
          height: 40px;
          padding-left: 20px;
          font-size: 13px;
          letter-spacing: 0.15px;
          border: none;
          outline: none;
          background-color: #ecf0f3;
          transition: 0.25s ease;
          border-radius: 8px;
          box-shadow: inset 2px 2px 4px #d1d9e6, inset -2px -2px 4px #f9f9f9;


          &:focus {
            box-shadow: inset 4px 4px 4px #d1d9e6, inset -4px -4px 4px #f9f9f9;

          }
        }
      }
    }

    .login {
      left: 400px;
      opacity: 1;
    }

    .register {
      left: 0px;
      opacity: 0;
    }

    .switch {
      display: flex;
      justify-content: center;
      align-items: center;
      position: absolute;
      top: 0;
      left: 0;
      height: 100%;
      width: 400px;
      padding: 50px;
      z-index: 200;
      transition: left 1.25s cubic-bezier(0.68, -0.55, 0.265, 1.55);
      background-color: #ecf0f3;
      overflow: hidden;
      box-shadow: 4px 4px 10px #d1d9e6, -4px -4px 10px #d1d9e6;
      animation: moveSwtich 1.25s;

      .circle {
        position: absolute;
        width: 500px;
        height: 500px;
        border-radius: 50%;
        background-color: #ecf0f3;
        box-shadow: inset 8px 8px 12px #e1c7da, inset -8px -8px 12px #fff;
        transition: transform 1s ease;
        transform: rotate(0deg);

        &:hover {
          transform: rotate(180deg);
        }



      }

      .switch-container {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        position: absolute;
        width: 400px;
        padding: 50px 55px;
        transition: 1.2s;
        pointer-events: none;
      }

      .switch-to-register {
        z-index: 99;

      }

      .switch-to-login {
        z-index: 99;
      }

      span {
        font-size: 30px;
        margin-bottom: 10px;
        letter-spacing: 0.25px;
        color: #181818;
        text-align: center;
        line-height: 1.6;
      }

      p {
        font-size: 16px;
      }
    }
  }
}
</style>
