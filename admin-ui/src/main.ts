import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import './assets/tailwind.css';

import { Amplify } from '@aws-amplify/core';
import awsmobile from './aws-exports';

Amplify.configure(awsmobile);

const clickOuside = {
  beforeMount(el: any, binding: any, vnode: any) {
    const vm = vnode.component?.appContext;
    const callback = binding.value;

    el.clickOutsideEvent = (event: { target: never }) => {
      if (!(el == event.target || el.contains(event.target))) {
        return callback.call(vm, event);
      }
    };
    document.body.addEventListener('click', el.clickOutsideEvent);
  },
  unmounted(el) {
    document.body.removeEventListener('click', el.clickOutsideEvent);
  }
};
createApp(App)
  .directive('click-outside', clickOuside)
  .use(router)
  .mount('#app');
