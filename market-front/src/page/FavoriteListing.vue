<template>
    <div class="container">
        <div style="width: 85%">
            <el-card>
                <div slot="header" class="clearfix"
                     style="display: flex; flex-direction: row; justify-content: space-between; align-items: center">
                    <span>Filter options</span>
                    <div style="float: right; padding: 0">
                        <el-button
                                @click="doFilter"
                                style="color: #2d8cf0; border-bottom: #2d8cf0 1px solid; border-radius: 0; width: 90px"
                                type="text">Do Filter
                        </el-button>
                    </div>
                </div>
                <div style="padding: 0 30px">
                    <div style="margin-bottom: 8px">
                        <span class="filterLabel">Filter by name: </span>
                        <el-input class="filterInput" v-model="filter.itemName"></el-input>
                    </div>
                    <div style="margin-bottom: 8px">
                        <span class="filterLabel">Filter by pickup location: </span>
                        <el-select class="filterInput" v-model="filter.location" clearable
                                   placeholder="Filter by pickup location">
                            <el-option
                                    v-for="(item, index) in locOptions"
                                    :key="index"
                                    :label="item"
                                    :value="index">
                            </el-option>
                        </el-select>
                    </div>
                    <div style="margin-bottom: 8px">
                        <span class="filterLabel">Filter by quality: </span>
                        <el-select class="filterInput" v-model="filter.quality" clearable
                                   placeholder="Filter by quality">
                            <el-option
                                    v-for="(item, index) in qualityOptions"
                                    :key="index"
                                    :label="item"
                                    :value="index">
                            </el-option>
                        </el-select>
                    </div>
                </div>
            </el-card>
            <el-card style="margin-top: 12px; height: auto">
                <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
                    <el-card v-for="(item, index) in data" :key="index"
                             style="margin: 12px; min-width: 300px; width: 30%">
                        <div slot="header" class="clearfix"
                             style="display: flex; flex-direction: row; justify-content: space-between; align-items: center">
                            <span>{{item.itemName}}</span>
                            <div style="float: right; padding: 0; display: flex; align-items: center">
                                <img @click="onDisLike(index)" src="../assets/icon/heart-fill.svg">
<!--                                <img @click="onDisLike(index)" src="../assets/icon/heart-fill.svg" v-if="item.isLike">-->
<!--                                <img @click="onLike(index)" src="../assets/icon/heart-line.svg" v-else>-->
                            </div>
                        </div>
                        <div class="listing-item-container">


                            <div style="display: flex; flex-direction: row; align-items: center;">
                                <span style="margin-right: 12px">
                                    Is Sold:
                                </span>
                                <el-switch
                                        style="margin-right: 12px">
                                        v-model="item.sold"
                                        :active-value="1"
                                        inactive-value="0"
                                        disabled>
                                </el-switch>
                                <span style="margin-right: 12px">
                                    Is Held:
                                </span>
                                <el-switch
                                        v-model="item.held"
                                        :active-value="1"
                                        inactive-value="0"
                                        disabled>
                                </el-switch>
                            </div>

                            <div>
                                Quantity: {{item.quantity}}
                            </div>

                            <div>
                                Quality: <span style="color: #2d8cf0">{{qualityOptions[item.quality]}}</span>
                            </div>

                            <div>
                                Pickup Location: <span style="color: red">{{locOptions[item.pickupLoc]}}</span>
                            </div>

<!--                            <div>-->
<!--                                Seller: <a href="#"> {{item.userByOwnerId.username}} </a> USC-ID: {{item.userByOwnerId.uscId}}-->
<!--                            </div>-->

<!--                            <div>-->
<!--                                Seller Contact: {{item.userByOwnerId.mobile}}-->
<!--                            </div>-->

<!--                            <div>-->
<!--                                Seller Email: {{item.userByOwnerId.email}}-->
<!--                            </div>-->

                            <div>
                                Description:
                            </div>
                            <div style="font-size: 14px; font-weight: 500; word-break: break-word; margin: 0 12px 16px">
                                {{item.itemDescription}}
                            </div>

                            <div></div>
                            <img :src="data[index].image">
                        </div>
                    </el-card>
                </div>
                <p style="text-align: center; margin: 20px">No more data.</p>
            </el-card>
        </div>
    </div>
</template>

<script>
  import {get, post, URL} from "../api";

  export default {
    name: "FavoriteListing",
    data() {
      return {
        data: [],
        rawData: [],
        loading: false,
        filter: {
          itemName: '',
          location: undefined,
          quality: undefined
        },
        locOptions: ['Tommy Trojan', 'Hecuba', 'Lyon Center', 'SAL', 'USC Bookstore', 'Leavey'],
        qualityOptions: ['Brand New', 'New-Open Box', 'Like New', 'Used-Very Good', 'Used-Good', 'Used-Acceptable'],
      }
    },
    mounted() {
      this.loadRaw();
    },
    methods: {
      loadData() {
        this.loadRaw()
      },
      loadRaw() {
        get(URL.Listing.myFavorite, {}).then(res => {
          this.rawData = res.data
          this.data = this.rawData
        })
      },
      doFilter() {
        let filter = [];
        for (let i in this.rawData) {
          let each = this.rawData[i]
          let filterName = this.filter.itemName.trim()
          if (filterName.length > 0 && !each.itemName.includes(filterName)) {
            continue
          }
          if (this.filter.location && each.pickupLoc !== this.filter.location) {
            continue
          }
          if (this.filter.quality && each.quality !== this.filter.quality) {
            continue
          }
          filter.push(each)
        }
        this.data = filter
      },
      onDisLike(index) {
        get(URL.Listing.removeFromFavorite, {
          listingId: this.data[index].id
        }).then(res=> {
          this.$message.success(res.data)
          this.loadData()
        })
      },
      onLike(index) {
        get(URL.Listing.addToFavorite, {
          listingId: this.data[index].id
        }).then(res=> {
          this.$message.success(res.data)
          this.loadData()
        })
      }
    }
  }
</script>

<style scoped>

    .listing-item-container {
        font-size: 15px;
    }

    .listing-item-container > div {
        margin: 8px 0;
    }

</style>
