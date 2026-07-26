<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const active = computed({ get: () => route.path, set: value => router.push(value) })
</script>

<template>
  <main class="app-shell"><router-view /></main>
  <van-tabbar v-model="active" route safe-area-inset-bottom active-color="#109a8f" inactive-color="#80929a">
    <van-tabbar-item name="/" to="/" icon="chart-trending-o">经营</van-tabbar-item>
    <van-tabbar-item name="/workbench" to="/workbench" icon="cluster-o">业务</van-tabbar-item>
    <van-tabbar-item name="/inventory" to="/inventory" icon="logistics">库存</van-tabbar-item>
    <van-tabbar-item name="/profile" to="/profile" icon="manager-o">我的</van-tabbar-item>
  </van-tabbar>
</template>

