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
                <div style="display: flex; flex-direction: row;flex-wrap: wrap; justify-content: space-between">
                    <el-card v-for="(item, index) in data" :key="index"
                             class="infinite-list-item"
                             style="margin: 12px; min-width: 300px; width: 30%">
                        <div slot="header" class="clearfix"
                             style="display: flex; flex-direction: row; justify-content: space-between; align-items: center">
                            <span>{{item.itemName}}</span>
                            <div style="float: right; padding: 0; display: flex; align-items: center">
                                <img @click="onDisLike(index)" src="../assets/icon/heart-fill.svg"
                                     v-if="fav.has(item.id)">
                                <img @click="onLike(index)" src="../assets/icon/heart-line.svg" v-else>
<!--                                @click="onPreview(index)"-->
                                <img @click="onPreview(index)" style="margin-left: 8px" src="../assets/icon/details.svg">
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
            </el-card>
        </div>
        <el-dialog title="Listing Details" :visible.sync="previewVisible" width="360">
            <div class="preview-listing-item-container">
                <div style="display: flex; flex-direction: row; align-items: center;">
                                <span style="margin-right: 12px">
                                    Is Sold:
                                </span>
                    <el-switch
                            style="margin-right: 12px">
                        v-model="previewItem.sold"
                        :active-value="1"
                        inactive-value="0"
                        disabled>
                    </el-switch>
                    <span style="margin-right: 12px">
                                    Is Held:
                                </span>
                    <el-switch
                            v-model="previewItem.held"
                            :active-value="1"
                            inactive-value="0"
                            disabled>
                    </el-switch>
                </div>

                <div>
                    Quantity: {{previewItem.quantity}}
                </div>

                <div>
                    Quality: <span style="color: #2d8cf0">{{qualityOptions[previewItem.quality]}}</span>
                </div>

                <div>
                    Pickup Location: <span style="color: red">{{locOptions[previewItem.pickupLoc]}}</span>
                </div>

                <div>
                    Seller: <a href="#"> {{previewItem.userByOwnerId.username}} </a>
                </div>

                <div>
                    Name: {{previewItem.userByOwnerId.realName}}
                </div>

                <div>
                    USC-ID: {{previewItem.userByOwnerId.uscId}}
                </div>

                <div>
                    Seller Contact: {{previewItem.userByOwnerId.mobile}}
                </div>

                <div>
                    Seller Email: {{previewItem.userByOwnerId.email}}
                </div>

                <div>
                    Description:
                </div>
                <div style="font-size: 14px; font-weight: 500; word-break: break-word; margin: 0 12px 16px">
                    {{previewItem.itemDescription}}
                </div>

                <div>
                    <img style="width: 100%; object-fit: fill; max-height: unset" :src="previewItem.image">
                </div>


            </div>
        </el-dialog>
    </div>
</template>

<script>
  import {get, post, URL} from "../api";
  import {KEY_STORE} from "../common/common";

  export default {
    name: "ListingSquare",
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
        lastLoadMore: false,
        fav: new Set(),
        previewVisible: false,
        previewItem: {
          userByOwnerId: {}
        }
      }
    },
    mounted() {
      this.loadRaw();
      this.scroll()
    },
    destroyed() {
      window.onscroll = null
    },
    computed: {
      info() {
        return JSON.parse(localStorage.getItem(KEY_STORE.INFO));
      }
    },
    methods: {
      scroll() {
        // window.onscroll = () => {
        //   console.log(document.body.scrollHeight + " " + window.scrollY + " " + window.innerHeight)
        //   if (document.body.scrollHeight === window.scrollY + window.innerHeight) {
        //     this.loadMore()
        //   }
        // }
      },
      loadMore() {
        console.log("load more")
      },
      onPreview(index) {
        this.previewVisible = true
        this.previewItem = this.data[index]
      },
      loadData() {
        this.loadRaw()
      },
      loadRaw() {
        get(URL.Listing.findAll, {}).then(res => {
          this.rawData = res.data
          this.data = this.rawData
          if (this.info) {
            get(URL.Listing.myFavorite, {}).then(res => {
              var fav = res.data
              fav = fav.map(x => x.id)
              this.fav = new Set(fav)
            })
          }
        });

      },
      doFilter() {
        let filter = [];
        console.log(this.filter)

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
          console.log(this.filter.location, each.pickupLoc)
          filter.push(each)
        }
        this.data = filter
      },
      onDisLike(index) {
        if (!this.info) {
          this.$message.warning("Please login first")
          return
        }
        get(URL.Listing.removeFromFavorite, {
          listingId: this.data[index].id
        }).then(res => {
          this.$message.success(res.data)
          this.loadData()
        })
      },
      onLike(index) {
        if (!this.info) {
          this.$message.warning("Please login first")
          return
        }
        get(URL.Listing.addToFavorite, {
          listingId: this.data[index].id
        }).then(res => {
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

    .preview-listing-item-container {
        font-size: 16px;
    }

    .preview-listing-item-container > div {
        margin: 12px 0;
    }
</style>
