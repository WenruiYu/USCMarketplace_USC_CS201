import {KEY_STORE} from '@/common/common';
import axios from 'axios';
import {toastError} from '@/common/common';

const service = axios.create({
  baseURL: 'http://localhost:9090',
  timeout: 5000
});


service.interceptors.request.use(
  config => {
    return config;
  },
  error => {
    console.log(error);
    return Promise.reject();
  }
);


function reject(error) {
  let data = error;
  if (error.response) {
    if (error.response.data) {
      data = error.response.data;
    } else {
      data = {
        code: error.response.status,
        msg: 'Network Service Error。'
      };
    }
  }
  console.log(data);
  toastError(data.msg);
  // if (error.code === 403) {
  // }
  return Promise.reject();
}

service.interceptors.response.use(
  response => {
    let data = response.data;
    if (data.code === 200) {
      return data;
    } else {
      // Promise.reject(data);
      return reject(data);
    }
  },
  reject
);

export const URL = {
  Auth: {
    login: '/auth/login',
    register: '/auth/register',
  },
  Account: {
    getInfo: '/account/getInfo',
    modifyInfo: '/account/modifyInfo',
    changePassword: '/account/changePassword',
  },
  Listing: {
    findAll: '/listing/findAll',
    addToFavorite: '/listing/addToFavorite',
    removeFromFavorite: '/listing/removeFromFavorite',
    myFavorite: '/listing/myFavorite',
    myListing: '/listing/myListing',
    addListingItem: '/listing/addListingItem',
    updateListingItem: '/listing/updateListingItem',
    deleteListingItem: '/listing/deleteListingItem',
  }
};

const WHITE_URL_PREFIX = [
  '/auth/',
  '/listing/findAll'
];

const TOKEN_HEADER = 'Authorization';


function checkRequireToken(url) {
  for (let i in WHITE_URL_PREFIX) {
    if (url.startsWith(WHITE_URL_PREFIX[i])) {
      return true;
    }
  }
  return false;
}


export const get = (url, param, header = {}) => {
  console.log(url);
  if (!checkRequireToken(url)) {
    header[TOKEN_HEADER] = localStorage.getItem(KEY_STORE.TOKEN);
  }
  return service.get(url, {
    headers: header,
    params: param
  });
};

export const post = (url, body, header = {}) => {
  console.log(url);
  if (!checkRequireToken(url)) {
    header[TOKEN_HEADER] = localStorage.getItem(KEY_STORE.TOKEN);
  }
  return service.post(url, body, {headers: header});
};
