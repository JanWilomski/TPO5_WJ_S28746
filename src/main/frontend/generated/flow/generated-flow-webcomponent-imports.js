import { injectGlobalWebcomponentCss } from 'Frontend/generated/jar-resources/theme-util.js';

import '@vaadin/common-frontend/ConnectionIndicator.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '1837dae755546f77b8eee07aaa7d9c9a5e1ed1dc9219120793dd00e503a2242d') {
    pending.push(import('./chunks/chunk-a1f98e73fc466598d51f269789cace1124342fec9dbc4a3881d7be3561f0cfbf.js'));
  }
  if (key === '52bd1e936057b0f3111fb4a6bd421d3c6a3c24e284c6d7bfca52095331f7df46') {
    pending.push(import('./chunks/chunk-ab03ea23e38047c1399e61d08e86b91b39662a1625e031bc6e8ba07c65926ba0.js'));
  }
  if (key === 'da687fa313ced61cb372afb1559f891412178b95234fa9fc362486efc93050f0') {
    pending.push(import('./chunks/chunk-ab03ea23e38047c1399e61d08e86b91b39662a1625e031bc6e8ba07c65926ba0.js'));
  }
  return Promise.all(pending);
}
window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}