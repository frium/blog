const lazyLoadDirecvtive = {
  mounted(el, binding) {
    const { value } = binding;
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            //进入视口
            const img = new Image();
            img.src = value;
            //图片加载成功
            img.onload = () => {
              el.src = value;
            };
            img.onerror = () => {
              // el.src = errorImage;
            };
            observer.unobserve(el); //停止观察
          }
        });
      },
      {
        rootMargin: "0px 0px 100px 0px", // 提前100px进入视口就开始加载
        threshold: 0.1, // 当10%的图片可见时触发
      }
    );
    // el.src = defaultImage;  //默认图片
    observer.observe(el);
    //将observer保存在元素上，便于在unmounted时断开连接
    el._lazyLoadObserver = observer;
  },
  unmounted(el) {
    if (el._lazyLoadObserver) el._lazyLoadObserver.disconnect();
  },
};
export default lazyLoadDirecvtive;
