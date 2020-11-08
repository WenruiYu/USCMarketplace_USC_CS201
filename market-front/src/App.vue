<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

  export default {
    name: 'App',
  }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }

    * {
        margin: 0;
        padding: 0;
    }

    html,
    body,
    #app,
    .wrapper {
        width: 100%;
        height: 100%;
    }

    body {
        font-family: 'PingFang SC', "Helvetica Neue", Helvetica, "microsoft yahei", arial, STHeiTi, sans-serif;
    }

    a {
        text-decoration: none
    }

    img {
        width: 100%;
        max-height: 200px;
        object-fit: cover;
    }


    [class*=" el-icon-lx"], [class^=el-icon-lx] {
        font-family: lx-iconfont !important;
    }

    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
        height: 100%;
    }


    .filterLabel {
        width: 200px;
        margin-right: 12px;
        display: inline-block;
    }

    .filterInput {
        width: 300px!important;
    }

    .listing-item-container {

    }

</style>


