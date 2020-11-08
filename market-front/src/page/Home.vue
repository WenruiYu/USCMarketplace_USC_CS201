<template>
    <el-container>
        <el-header>
            <div style="position: relative">
                <el-menu :default-active="activeIndex" router class="el-menu-demo" mode="horizontal">
                    <el-menu-item index="/">Listing Square</el-menu-item>
                    <el-menu-item index="/favorite">Favorite List</el-menu-item>
                    <el-menu-item index="/personal">My Listings</el-menu-item>
                    <el-menu-item index="/account">Account</el-menu-item>
                </el-menu>

                <div style="position: absolute; right: 12px; top: 0; bottom: 0; display: flex; align-items: center">
                    <div class="menu-button">Logout</div>
                </div>
            </div>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        activeIndex: this.$route.path,
      }
    },
  }
</script>

<style scoped>
    .menu-button {
        color: #2d8cf0;
        font-size: 14px;
        border-bottom: #2d8cf0 1px solid
    }
</style>
