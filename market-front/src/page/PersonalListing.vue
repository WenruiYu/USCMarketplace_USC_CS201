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
                        <el-button style="color: red; border-bottom: red 1px solid; border-radius: 0; width: 90px;"
                                   @click="createVisible = true" type="text">Create Listing
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
                    <el-card v-for="(i, index) in data" :key="index" style="margin: 12px; min-width: 300px; width: 30%">
                        <div slot="header" class="clearfix"
                             style="display: flex; flex-direction: row; justify-content: space-between; align-items: center">
                            <span>{{data[index].itemName}}</span>
                            <div style="float: right; padding: 0">
                                <el-button
                                        @click="onSaveItem(index)"
                                        style="color: #2d8cf0;"
                                        type="text" v-if="data[index].isInEdit">Save
                                </el-button>

                                <el-button
                                        @click="onEditItem(index)"
                                        style="color: orange; "
                                        type="text" v-else>Edit
                                </el-button>

                                <el-button style="color: red;"
                                           @click="onDeleteItem(index)" type="text">Delete
                                </el-button>
                            </div>
                        </div>
                        <div class="listing-item-container">
                            <el-form ref="form" :model="data[index]">
                                <el-form-item label="Item Name">
                                    <el-input placeholder="Item name" v-model="data[index].itemName"
                                              :disabled="!data[index].isInEdit"></el-input>
                                </el-form-item>
                                <el-form-item label="Item Description">
                                    <el-input type="textarea" placeholder="Item name"
                                              v-model="data[index].itemDescription"
                                              :disabled="!data[index].isInEdit"></el-input>
                                </el-form-item>

                                <el-form-item label="Is Sold">
                                    <el-switch
                                            v-model="data[index].sold"
                                            :active-value="1"
                                            inactive-value="0"
                                            :disabled="!data[index].isInEdit">
                                    </el-switch>
                                </el-form-item>

                                <el-form-item label="Is Held">
                                    <el-switch
                                            v-model="data[index].held"
                                            :active-value="1"
                                            inactive-value="0"
                                            :disabled="!data[index].isInEdit">
                                    </el-switch>
                                </el-form-item>

                                <el-form-item label="Image URL">
                                    <el-input placeholder="Image URL" v-model="data[index].image"
                                              :disabled="!data[index].isInEdit"></el-input>
                                </el-form-item>

                                <el-form-item label="Quantity">
                                    <el-input-number v-model="data[index].quantity" :min="1"
                                                     :disabled="!data[index].isInEdit"></el-input-number>
                                </el-form-item>

                                <el-form-item label="Quality">
                                    <el-select v-model="data[index].quality" :disabled="!data[index].isInEdit">
                                        <el-option
                                                v-for="(item, index) in qualityOptions"
                                                :key="index"
                                                :label="item"
                                                :value="index">
                                        </el-option>
                                    </el-select>
                                </el-form-item>

                                <el-form-item label="Pickup Location">
                                    <el-select v-model="data[index].pickupLoc" :disabled="!data[index].isInEdit">
                                        <el-option
                                                v-for="(item, index) in locOptions"
                                                :key="index"
                                                :label="item"
                                                :value="index">
                                        </el-option>
                                    </el-select>
                                </el-form-item>

                                <el-form-item label="Preview">
                                    <img :src="data[index].image">
                                </el-form-item>
                            </el-form>
                        </div>
                    </el-card>
                </div>
                <p style="text-align: center; margin: 20px">No more data.</p>
            </el-card>
        </div>

        <el-dialog title="Create Listing" :visible.sync="createVisible" width="360">
            <el-form ref="form" :model="createForm" label-width="150px">
                <el-form-item label="Item Name">
                    <el-input placeholder="Item name" v-model="createForm.itemName"></el-input>
                </el-form-item>
                <el-form-item label="Item Description">
                    <el-input type="textarea" placeholder="Item name" v-model="createForm.itemDescription"></el-input>
                </el-form-item>
                <el-form-item label="Image URL">
                    <el-input placeholder="Image URL" v-model="createForm.image"></el-input>
                </el-form-item>

                <el-form-item label="Quantity">
                    <el-input-number v-model="createForm.quantity" :min="1"></el-input-number>
                </el-form-item>

                <el-form-item label="Quality">
                    <el-select v-model="createForm.quality">
                        <el-option
                                v-for="(item, index) in qualityOptions"
                                :key="index"
                                :label="item"
                                :value="index">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="Pickup Location">
                    <el-select v-model="createForm.pickupLoc">
                        <el-option
                                v-for="(item, index) in locOptions"
                                :key="index"
                                :label="item"
                                :value="index">
                        </el-option>
                    </el-select>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="createVisible = false">Cancel</el-button>
                <el-button type="primary" @click="saveCreate">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {get, post, URL} from "../api";

  export default {
    name: "PersonalListing",
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
        createVisible: false,
        createForm: {
          itemName: '',
          itemDescription: '',
          quantity: 1,
          quality: 0,
          image: '',
          pickupLoc: 0,

        }
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
        get(URL.Listing.myListing, {}).then(res => {
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
      saveCreate() {
        console.log('save')
        post(URL.Listing.addListingItem, this.createForm).then(res => {
          this.$message.success(res.data)
          this.createForm = {
            itemName: '',
            itemDescription: '',
            quantity: 1,
            quality: 0,
            image: '',
            pickupLoc: 0,
          };
          this.createVisible = false
        })
      },
      onEditItem(index) {
        this.data[index].isInEdit = true
        this.$set(this.data, index, this.data[index])
      },
      onDeleteItem(index) {
        get(URL.Listing.deleteListingItem, {listing: this.data[index].id}).then(res => {
          this.$message.success(res.data)
          this.loadData()
        })
      },
      onSaveItem(index) {
        post(URL.Listing.updateListingItem, this.data[index]).then(res => {
          this.$message.success(res.data)
          this.data[index].isInEdit = false
          this.$set(this.data, index, this.data[index])
        })
      }
    }
  }
</script>

<style scoped>

</style>
