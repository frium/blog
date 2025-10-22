import lazyLoadDirective from "./lazyLoadDirective";
export const setupDirectives = (app) => {
  app.directive("lazyLoad", lazyLoadDirective);
};
