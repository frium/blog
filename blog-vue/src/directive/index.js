import lazyLoadDirective from "./lazyLoadDirective";
import permissionDirective from "./permissionDirective";
export const setupDirectives = (app) => {
  app.directive("lazyLoad", lazyLoadDirective);
  // app.directive("permission", permissionDirective);
};
