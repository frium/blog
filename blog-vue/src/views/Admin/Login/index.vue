<script setup>
import { ref } from 'vue';

const regUsernameCheck = ref(true);
const regPasswordCheck = ref(true);
const usernameCheck = ref(true);
const passwordCheck = ref(true);

const usernameErrorMsg = ref('')
const passwordErrorMsg = ref('')
const regUsernameErrorMsg = ref('')
const regPasswordErrorMsg = ref('')

const user = {
  username: '',
  password: ''
}
const login = ref({ user });
const register = ref({ user });
const isLogin = ref(true);//switch控制状态
const changState = () => {
  isLogin.value = !isLogin.value
  setTimeout(() => {
    regUsernameCheck.value = true;
    regPasswordCheck.value = true;
    usernameCheck.value = true;
    passwordCheck.value = true;
    login.value.username = '';
    login.value.password = '';
    register.value.username = '';
    register.value.password = '';
  }, 600)
}

const checkUsername = () => {
  if (!login.value.username) {
    usernameCheck.value = false;
    usernameErrorMsg.value = '用户名不能为空!'
    return;
  }
  login.value.username = login.value.username.trim();
  if (login.value.username === '') {
    usernameCheck.value = false;
    usernameErrorMsg.value = '用户名不能为空!'
    return;
  }
  if (!/^[a-zA-Z0-9]+$/.test(login.value.username)) {
    usernameCheck.value = false;
    usernameErrorMsg.value = '用户名不能包含特殊字符!'
    return;
  }
  usernameCheck.value = true;
}
const checkRegUsername = () => {
  if (!register.value.username) {
    regUsernameCheck.value = false;
    regUsernameErrorMsg.value = '用户名不能为空!'
    return;
  }
  register.value.username = register.value.username.trim();
  if (register.value.username === '') {
    regUsernameCheck.value = false;
    regUsernameErrorMsg.value = '用户名不能为空!'
    return;
  }
  if (!/^[a-zA-Z0-9]+$/.test(register.value.username)) {
    regUsernameCheck.value = false;
    regUsernameErrorMsg.value = '用户名不能包含特殊字符!';
    return;
  }
  if (!/^.{6,16}$/.test(register.value.username)) {
    regUsernameCheck.value = false;
    regUsernameErrorMsg.value = '用户名长度应在6~16之间!';
    return;
  }
  regUsernameCheck.value = true;
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

const signin = () => {
  checkUsername();
  checkPassword();
  if (!usernameCheck.value && !passwordCheck.value) return;
  //登录操作
  console.log('登录');

}

const signup = () => {
  checkRegUsername();
  checkRegPassword();
  if (!regUsernameCheck.value && !regPasswordCheck.value) return;
  //注册操作
  console.log('注册');

}


</script>

<template>
  <div class="container">
    <div class="shell">
      <div class="register box  "
        :style="{ left: isLogin ? '400px' : '0px', opacity: isLogin ? 0 : 1, 'z-index': isLogin ? 0 : 100 }">
        <form action="" class="register-form" @submit.prevent>
          <span>创建账号</span>
          <div class="input-container">
            <input type="text" placeholder="username" v-model="register.username" @blur="checkRegUsername()">
            <div v-if="!regUsernameCheck" class="error-msg">
              <span>{{ regUsernameErrorMsg }} </span>
            </div>
          </div>
          <div class="input-container">
            <input type="password" placeholder="password" v-model="register.password" @blur="checkRegPassword()">
            <div v-if="!regPasswordCheck" class="error-msg">
              <span>{{ regPasswordErrorMsg }}</span>
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
            <input type="text" placeholder="username" v-model="login.username" @blur="checkUsername()">
            <div v-if="!usernameCheck" class="error-msg">
              <span>{{ usernameErrorMsg }}</span>
            </div>
          </div>
          <div class="input-container">
            <input type="password" placeholder="password" v-model="login.password" @blur="checkPassword()">
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
div {
  font-family: 'LXGW WenKai Regular', 'Noto Serif SC';
}

span {
  font-family: 'LXGW WenKai Regular', 'Noto Serif SC';
}

a {
  font-family: 'LXGW WenKai Regular', 'Noto Serif SC';
}

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


.container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

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
    box-shadow: 10px 10px 10px rgb(164, 160, 160);
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

        &:nth-of-type(2) {
          input {
            margin-top: 22px;
          }
        }

        .error-msg {
          margin-top: 2px;
          margin-left: 8px;
          position: absolute;
          font-size: 2px;

          span {
            padding: 2px 0;
            font-size: 4px;
            line-height: 0;
            color: #dd2727;
            letter-spacing: 2px;
          }
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
