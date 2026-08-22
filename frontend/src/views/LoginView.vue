<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { showSuccessToast } from 'vant'
import { useAuthStore } from '../stores/auth'

const form = reactive({ username: 'demo', password: 'Demo@2026' })
const loading = ref(false)
const router = useRouter()
const auth = useAuthStore()

async function submit() {
  loading.value = true
  try {
    await auth.login(form)
    showSuccessToast('经营数据已就绪')
    router.replace('/')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-page safe-top">
    <div class="brand-row"><span class="brand-mark">ZH</span><span>知华科技</span></div>
    <section class="login-hero">
      <div class="eyebrow">ZHUATECH ERP · COMMUNITY</div>
      <h1>把经营数据<br><em>装进口袋</em></h1>
      <p>销售、采购、库存与资金，一张图实时掌握。</p>
      <div class="mini-board">
        <div><span>本月销售</span><strong>¥160,080</strong></div>
        <i></i>
        <div><span>库存预警</span><strong>1 项</strong></div>
      </div>
    </section>
    <section class="login-card">
      <h2>登录经营工作台</h2>
      <van-field v-model="form.username" label="账号" placeholder="请输入账号" left-icon="contact-o" />
      <van-field v-model="form.password" label="密码" type="password" placeholder="请输入密码" left-icon="closed-eye" />
      <van-button block round color="#0b2538" :loading="loading" @click="submit">进入系统</van-button>
      <div class="demo-tip">经理演示：demo / Demo@2026<br>管理员：admin / ZhuaTech@2026</div>
    </section>
    <a class="company" href="https://www.zhuatech.cn/" target="_blank" rel="noopener">上海如静知华信息科技有限公司</a>
  </div>
</template>

<style scoped>
.login-page{min-height:100vh;padding:24px 22px 30px;background:linear-gradient(160deg,#071d2c 0,#0d3143 52%,#f3f7f8 52.1%);color:#fff}.brand-row{display:flex;align-items:center;gap:10px;font-weight:700;font-size:13px;letter-spacing:.5px}.brand-mark{width:34px;height:34px;display:grid;place-items:center;border-radius:10px;background:#22b8aa;color:#fff;font-size:12px}.login-hero{padding:45px 4px 32px}.eyebrow{color:#6ee0d5;font-size:10px;font-weight:700;letter-spacing:1.7px}.login-hero h1{font-size:39px;line-height:1.18;margin:13px 0 12px;letter-spacing:-1.6px}.login-hero em{color:#52d5c8;font-style:normal}.login-hero p{color:#b8cbd2;font-size:13px}.mini-board{display:flex;align-items:center;gap:17px;margin-top:25px}.mini-board div{display:flex;flex-direction:column;gap:5px}.mini-board span{font-size:10px;color:#8daab4}.mini-board strong{font-size:16px}.mini-board i{width:1px;height:33px;background:#315468}.login-card{color:#173140;background:#fff;border-radius:24px;padding:23px 18px 20px;box-shadow:0 22px 50px rgba(4,26,39,.18)}.login-card h2{margin:0 0 12px;font-size:18px}.van-button{margin-top:20px}.demo-tip{text-align:center;color:#8a9ba3;font-size:11px;line-height:1.8;margin-top:13px}.company{display:block;text-align:center;color:#6d818b;text-decoration:none;font-size:11px;margin-top:23px}
</style>

