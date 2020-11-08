import Vue from 'vue';

export const KEY_STORE = {
    USERNAME: 'tmpUsername',
    MOBILE: 'tmpMobile',
    TOKEN: 'token',
    INFO: 'info'
};

export const toastError = (msg) => {
    Vue.prototype.$message.error(msg);
};

export const toastSuccess = (msg) => {
    Vue.prototype.$message.success(msg);
};

export const to403 = () => {
    router.push({
        path: '/403'
    });
};

export const getDay = () => {
    let ts = new Date();
    ts.setHours(0);
    ts.setMinutes(0);
    ts.setSeconds(0);
    ts.setMilliseconds(0);
    return ts.getTime();
};
