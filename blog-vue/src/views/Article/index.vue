<template>
  <div class="container">
    <div class="img"></div>
    <MarkdownViewer class="markdown" :source="mdContent" :line-numbers="true" />
  </div>
</template>

<script setup>
import MarkdownViewer from './components/MarkdownViewer.vue'
import { ref } from 'vue'

const mdContent = ref(`
# Markdown 全面示例

## 文本格式
**粗体文本**
*斜体文本*
~~删除线~~
这是行内\`代码片段\`

## 列表类型

### 无序列表
- Vue 3
- Markdown-it
- Highlight.js
  - 子项1
  - 子项2

### 有序列表
1. 安装依赖
2. aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
3. 使用示例

## 代码展示
\`\`\`java
public static void main(String[] args) {
        long max=0;
        for (long i = 10000000; i >0 ; i--) {
            if(get(i)){
                max= i;
                break;
            }
        }
        System.out.println(max);
    }
    public static boolean get(long n){
        long []arr=new long[100000];//获取n的位数
        arr[0]=n/1000000%10;
        arr[1]=n/100000%10;
        arr[2]=n/10000%10;
        arr[3]=n/1000%10;
        arr[4]=n/100%10;
        arr[5]=n/10%10;
        arr[6]=n%10;
        for (int i = 7; i < arr.length; i++) {
            long number =arr[i-1]+arr[i-2]+arr[i-3]+arr[i-4]+arr[i-5]+arr[i-6]+arr[i-7];
            //当有一个数都大于我们要寻找的数时,那他再加上前面我们的几位数肯定会比我们要找的数大,这里就直接停止寻找返回false了
            if(number>n)return  false;
            if(number==n)return true;//说明这个数在我们这个数列当中,满足题干
            arr[i]=number;//将前七位总和的数添加进去
        }
        return false;
    }
\`\`\`


## 图片显示

## 表格示例
| 功能        | 支持状态     | 备注                      |
|-------------|-------------|---------------------------|
| 语法高亮    | ✔️ 已支持    | 使用highlight.js          |
| 表格        | ✔️ 已支持    | 需要markdown-it扩展       |
| 数学公式    | ✖️ 未支持    | 需要安装mathjax插件       |

## 引用块
> "优秀的组件应该像Vue一样简洁优雅。"
> —— 前端开发者
\`\`\`java
public static void main(String[] args) {
        long max=0;
        for (long i = 10000000; i >0 ; i--) {
            if(get(i)){
                max= i;
                break;
            }
        }
        System.out.println(max);
    }
    public static boolean get(long n){
        long []arr=new long[100000];//获取n的位数
        arr[0]=n/1000000%10;
        arr[1]=n/100000%10;
        arr[2]=n/10000%10;
        arr[3]=n/1000%10;
        arr[4]=n/100%10;
        arr[5]=n/10%10;
        arr[6]=n%10;
        for (int i = 7; i < arr.length; i++) {
            long number =arr[i-1]+arr[i-2]+arr[i-3]+arr[i-4]+arr[i-5]+arr[i-6]+arr[i-7];
            //当有一个数都大于我们要寻找的数时,那他再加上前面我们的几位数肯定会比我们要找的数大,这里就直接停止寻找返回false了
            if(number>n)return  false;
            if(number==n)return true;//说明这个数在我们这个数列当中,满足题干
            arr[i]=number;//将前七位总和的数添加进去
        }
        return false;
    }
\`\`\`
## 分割线
---

## 链接

## 注意事项
- [x] 完成组件开发
- [ ] 添加测试用例
- [ ] 编写文档
`)
</script>
<style scoped lang="scss">
.container {
  width: 1000px;
  background: rgba(40, 44, 52, 0.6);
  padding: 10px 20px;

  .markdown {
    width: 100%;
  }
}
</style>
