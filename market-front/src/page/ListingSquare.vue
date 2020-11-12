<template>
    <div class="container">
        <div style="width: 85%">
            <el-card style="margin-top: 15px;">  
                <div class="clearfix" style="display: flex; flex-direction: row; align-items: center;">                     
                    <span class="filterLabel searchTerm">Search Term:</span>
                    <el-input class="filterInput" v-model="filter.itemName"></el-input>

                    <el-select class="filterInput" v-model="filter.location" clearable
                                placeholder="Filter by pickup location">
                        <el-option
                                v-for="(item, index) in locOptions"
                                :key="index"
                                :label="item"
                                :value="index">
                        </el-option>
                    </el-select>

                    <el-select class="filterInput" v-model="filter.quality" clearable
                                placeholder="Filter by quality">
                        <el-option
                                v-for="(item, index) in qualityOptions"
                                :key="index"
                                :label="item"
                                :value="index">
                        </el-option>
                    </el-select>

                    <div class="search-button" @click="doFilter">Search</div>
                </div>
            </el-card>
            <el-card style="margin-top: 12px; height: auto">
                <div style="display: flex; flex-direction: row; flex-wrap: wrap; justify-content: space-around">
                    <p v-if="data.length == 0" style="align: center; margin: 20px">There are no listings yet, go post one!</p>
                    <el-card v-for="(item, index) in data" :key="index"
                             class="infinite-list-item"
                             style="margin: 12px; min-width: 300px; width: 30%;">
                        <div slot="header" class="clearfix"
                            style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
                            <span @click="onPreview(index)" style="cursor: pointer;">{{item.itemName}}</span>
                            <div style="float: right; padding: 0; display: flex; align-items: center; cursor: pointer">
                                <img @click="onDisLike(index)" src="../assets/icon/heart-fill.svg"
                                    v-if="fav.has(item.id)">
                                <img @click="onLike(index)" src="../assets/icon/heart-line.svg" v-else>
                            </div>
                        </div>
                        <div class="listing-item-container" @click="onPreview(index)">
                            <p>Description:</p>
                            <p style="font-size: 14px; font-weight: 500; word-break: break-word; margin: 0 12px 16px">{{item.itemDescription}}</p>
                            <p>Quantity: <span style="color: green">{{item.quantity}}</span></p>
                            <p>Quality: <span style="color: #2d8cf0">{{qualityOptions[item.quality]}}</span></p>
                            <p>Pickup Location: <span style="color: red">{{locOptions[item.pickupLoc]}}</span></p>
                            <p>
                              Availability: 
                              <span v-if="item.sold == 1" style="color: red">Item has been sold</span>
                              <span v-else-if="item.held == 1" style="color: orange">Item is being held</span>
                              <span v-else style="color: green">Available</span>
                            </p>
                            <div class="item-image-container"><img class="item-image" :src="data[index].image"></div>
                        </div>
                    </el-card>
                </div>
            </el-card>
        </div>
        <el-dialog :visible.sync="previewVisible" width="360">
            <div slot="title" class="preview-title">{{previewItem.itemName}}</div>
            <div class="preview-listing-item-container">
                <div class="left-column">
                    <p>Description:</p>
                    <div style="font-size: 14px; font-weight: 500; word-break: break-word; margin: 0 12px 16px">
                        {{previewItem.itemDescription}}
                    </div><br>

                    <div class="preview-image-container"><img class="preview-image" :src="previewItem.image"></div>
                </div>
                <div class="right-column">
                    <p>Pickup Location: <span style="color: red">{{locOptions[previewItem.pickupLoc]}}</span></p>
                    <p>Quality: <span style="color: #2d8cf0">{{qualityOptions[previewItem.quality]}}</span></p>
                    <p>Quantity: {{previewItem.quantity}}</p><br>

                    <div>
                        <span style="margin-right: 8px">Is Sold:</span>
                        <el-switch style="margin-right: 12px"
                            v-model="previewItem.sold"
                            :active-value="1"
                            inactive-value="0"
                            disabled>
                        </el-switch>
                        <span style="margin-right: 8px">Is Held:</span>
                        <el-switch
                            v-model="previewItem.held"
                            :active-value="1"
                            inactive-value="0"
                            disabled>
                        </el-switch>
                    </div><br>

                    <p>Seller: <a href="#">{{previewItem.userByOwnerId.username}}</a></p>   
                    <p>Name: {{previewItem.userByOwnerId.realName}}</p> 
                    <p>USC-ID: {{previewItem.userByOwnerId.uscId}}</p>
                    <p>Seller Contact: {{previewItem.userByOwnerId.mobile}}</p>
                    <p>Seller Email: {{previewItem.userByOwnerId.email}}</p>
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
    .searchTerm {
        width: 110px;
        margin-right: 0px;
    }

    .filterInput {
        margin-right: 20px;
    }

    .search-button {
        color: #2d8cf0;
        font-size: 18px;
        border-bottom: #2d8cf0 1px solid;
        padding-bottom: 5px;
        cursor: pointer;
        width: 90px;
        text-align: center;
    }
    
    .search-button:hover {
        color: #0061c7;
    }

    .listing-item-container {
        font-size: 15px;
        cursor: pointer;
    }

    .left-column {
        flex: 1;
        padding-bottom: 1rem;
        padding-top: 0rem;
        text-align: left;
    }

    .right-column {
        flex: 1;
        padding-bottom: 1rem;
        padding-top: 0rem;
        text-align: right;
    }

    .listing-item-container > p {
        margin: 8px 0;
    }

    .item-image-container {
        height: auto;
        width: 50%; 
        margin-top: 20px;
    }

    .item-image {
        float: center;
        max-height: 150px;
        position: static;
        border: 1px solid gray;
        object-fit: cover;
    }
        
    .preview-title {
        font-size: 28px;
        font-weight: 500;
        margin-left: 15px;
        margin-top: 10px;
        padding-bottom: 0px;
    }

    .preview-image-container {
        height: auto;
        width: 100%; 
    }

    .preview-image {
        float: left;
        max-height: 360px;
        position: relative;
        border: 1px solid gray;
        object-fit: cover;
    }

    .preview-listing-item-container {
        display: flex;
        font-size: 16px;
        margin-left: 20px;
        margin-right: 20px;
    }
</style>
