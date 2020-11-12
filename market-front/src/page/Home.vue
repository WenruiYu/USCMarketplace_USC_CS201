<template>
    <el-container>
        <el-header>
            <div style="position: relative;">
                <el-menu :default-active="activeIndex"  @tab-click="handleClick" router class="el-menu-demo" mode="horizontal">
                    <a href="#/"><img class="logo" src="../assets/img/scmarketplace_logo_H.png"></a>
                    <el-menu-item class="navbutton listingButton" index="/">Listing Square</el-menu-item>
                    <el-menu-item class="navbutton" index="/favorite">Favorite Listings</el-menu-item>
                    <el-menu-item class="navbutton" index="/personal">My Listings</el-menu-item>
                    <el-menu-item class="navbutton" index="/account">Account</el-menu-item>
                </el-menu>

                <div style="position: absolute; right: 12px; top: 0; bottom: 0; display: flex; align-items: center">
                    <div class="menu-button" @click="toLogout">Logout</div>
                </div>
            </div>
        </el-header>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>
  import {KEY_STORE} from "../common/common";

  export default {
    name: "Home",
    data() {
      return {
        activeIndex: this.$route.path,
      }
    },
    methods: {
      toLogout() {
        localStorage.removeItem(KEY_STORE.INFO);
        localStorage.removeItem(KEY_STORE.TOKEN);
        this.$router.push('/login')
      },
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  }
</script>

<style scoped>
    .el-menu {
        height: 75px;
    }

    .logo {
        display: flex;
        width: 210px;
        position: absolute;
        margin-left: 5px;
    }

    .listingButton {
        margin-left: 225px;
    }

    .navbutton {
        height: 75px;
        line-height: 75px;
        font-size: 18px;
    }

    .menu-button {
        color: #2d8cf0;
        font-size: 18px;
        border-bottom: #2d8cf0 1px solid;
        padding-bottom: 5px;
        cursor: pointer;
    }
    .menu-button:hover {
        color: #0061c7;
    }
</style>
