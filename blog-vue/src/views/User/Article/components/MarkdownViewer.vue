<template>
  <div id="markdown-container" v-html="htmlContent" />
</template>

<script setup>
import { nextTick, ref, watchEffect } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.min.css'
import multimdTable from 'markdown-it-multimd-table'


const props = defineProps({
  source: {
    type: String,
    default: ''
  },
  // 是否显示行号（需要自定义样式支持）
  lineNumbers: {
    type: Boolean,
    default: false
  }
})
// 初始化markdown-it实例
const md = new MarkdownIt({
  html: true,         // 允许HTML标签
  linkify: true,      // 自动转换URL为链接
  typographer: true,  // 优化排版
  highlight: function (str, lang) {
    // 此处判断是否有添加代码语言
    if (lang && hljs.getLanguage(lang)) {
      try {
        // 得到经过highlight.js之后的html代码
        const preCode = hljs.highlight(lang, str, true).value
        // 以换行进行分割
        const lines = preCode.split(/\n/).slice(0, -1);
        // 拼接成li
        let html = lines.map((item) => {
          return '<li>' + item + '</li>'
        }).join('')
        html = '<ol>' + html + '</ol>'
        // 添加代码语言
        // highlight 函数内
        const a = `
  <div class="function-code">
    <b class="lang">${lang}</b>
    <svg
      t="1739988398935"
      class="show-code"
      viewBox="0 0 1024 1024"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
      p-id="5418"
      width="14"
      height="14"
    >
      <path
        d="M512.365 685.995l433.68-433.68c17.574-17.574 46.066-17.574 63.64 0 17.574 17.574 17.574 46.066 0 63.64L562.57 763.07c-27.727 27.727-72.682 27.727-100.41 0L15.046 315.955c-17.573-17.574-17.573-46.066 0-63.64 17.574-17.574 46.066-17.574 63.64 0l433.68 433.68z"
        p-id="5419"
      ></path></svg>
    <svg
      t="1739987442008"
      class="copy-code"
      viewBox="0 0 1024 1024"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
      p-id="3579"
      width="14"
      height="14"
    >
      <path
        d="M638.596211 191.936191q30.628116 0 54.62014 13.272183t41.347956 32.66999 26.544367 41.858425 9.188435 39.81655l0 576.829511q0 29.607178-11.740778 53.088734t-30.628116 39.81655-42.368893 25.52343-46.963111 9.188435l-503.322034 0q-19.397807 0-42.368893-11.230309t-42.879362-29.607178-33.180459-42.368893-13.272183-48.494516l0-568.662014q0-21.439681 10.209372-44.410768t26.544367-42.368893 37.774676-32.159521 44.921236-12.761715l515.57328 0zM578.360917 830.021934q26.544367 0 45.431705-18.376869t18.887338-44.921236-18.887338-45.431705-45.431705-18.887338l-382.851446 0q-26.544367 0-45.431705 18.887338t-18.887338 45.431705 18.887338 44.921236 45.431705 18.376869l382.851446 0zM578.360917 574.787637q26.544367 0 45.431705-18.376869t18.887338-44.921236-18.887338-45.431705-45.431705-18.887338l-382.851446 0q-26.544367 0-45.431705 18.887338t-18.887338 45.431705 18.887338 44.921236 45.431705 18.376869l382.851446 0zM759.0668 0q43.900299 0 80.654038 26.033898t63.808574 64.319043 42.368893 82.695912 15.314058 81.164506l0 542.117647q0 21.439681-12.761715 39.306082t-31.138584 30.628116-39.81655 20.418744-39.81655 7.657029l-4.083749 0 0-609.499501q-8.167498-70.444666-43.900299-108.219342t-94.947159-49.004985l-498.217348 0q1.020937-2.041874 1.020937-7.14656 0-20.418744 12.251246-41.858425t32.159521-38.795613 44.410768-28.586241 49.004985-11.230309l423.688933 0z"
        p-id="3580"
      ></path>
    </svg>
  </div>
`;
        return '<pre class="hljs">' + a + '<code>' +
          html +
          '</code></pre>'
      } catch (__) { }
    }
  }
}).use(multimdTable)

const handleShowCode = (event) => {
  const svg = event.currentTarget;
  const codeWrapper = svg.closest('.hljs');
  const targetCode = codeWrapper?.querySelector('pre code ol');

  if (targetCode.style.maxHeight === '0px') {
    targetCode.style.maxHeight = 'none';
    const fullHeight = targetCode.scrollHeight + 'px';
    targetCode.style.maxHeight = '0px';
    requestAnimationFrame(() => {
      targetCode.style.maxHeight = fullHeight;
      svg.style.transform = 'rotate(0deg)';
      targetCode.style.paddingBottom = '15px';
      setTimeout(() => {
        targetCode.style.overflow = 'auto';
      }, 300);
    });
  } else {
    targetCode.style.maxHeight = '0px';
    svg.style.transform = 'rotate(90deg)';
    targetCode.style.paddingBottom = '0px';
    targetCode.style.overflow = 'hidden';
  }
}

const handleCopyCode = async (event) => {
  try {
    const target = event.currentTarget.closest('.hljs');
    const targetCode = target?.querySelector('pre code ol');
    const lines = Array.from(targetCode.querySelectorAll('li')).map(li => {
      return Array.from(li.childNodes)
        .map(node => {
          let text = node.textContent;
          if (node.nodeType === Node.TEXT_NODE) {
            text = text.replace(/^\s+/, s => ' '.repeat(s.length)); // 转换开头的空白
          }
          return text;
        })
        .join('')
        .replace(/\u00A0/g, ' '); // 处理&nbsp;
    });

    // 添加显式换行符
    const formattedText = lines.join('\n')
      .replace(/\n{3,}/g, '\n\n') // 压缩多个空行
      .trimEnd();
    await navigator.clipboard.writeText(formattedText);
  } catch (err) {
    console.error('复制失败:', err);
  }
};

const htmlContent = ref('');
watchEffect(() => {
  htmlContent.value = md.render(props.source);
  nextTick(() => {
    document.querySelectorAll('.show-code').forEach(svg => {
      svg.removeEventListener('click', handleShowCode)
      svg.addEventListener('click', handleShowCode)
    });
    document.querySelectorAll('.copy-code').forEach(svg => {
      svg.removeEventListener('click', handleCopyCode)
      svg.addEventListener('click', handleCopyCode)
    });
    emit('component-loaded');
  })
});

const emit = defineEmits(['component-loaded']);

</script>

<style scoped lang="scss">
#markdown-container {
  background: var(--bg-color);
  padding: .6rem 1rem 1rem;
  border-radius: 10px;
  user-select: text;
  width: 100%;
  overflow-wrap: break-word;
  line-height: 1.6;
  display: flow-root;


  :deep() {

    /* 有序列表和无序列表样式 */
    ul,
    ol {
      padding-left: 2em;
      line-height: 1.6;

      li {
        margin-bottom: 0.5em;
        position: relative;
      }
    }

    /* 无序列表的圆点样式 */
    ul {
      list-style-type: disc;

      ul {
        list-style-type: circle;

        ul {
          list-style-type: square;
        }
      }
    }

    /* 有序列表的数字样式 */
    ol {
      list-style-type: decimal;

      ol {
        list-style-type: lower-alpha;

        ol {
          list-style-type: lower-roman;
        }
      }
    }

    /* 任务列表样式 */
    .contains-task-list {
      padding-left: 0;
      list-style: none;
    }
  }

  :deep() {
    h1 {
      font-size: 2em;
      margin: 0.67em 0;
    }

    h2 {
      font-size: 1.5em;
      margin: 0.83em 0;
    }

    h3 {
      font-size: 1.17em;
      margin: 1em 0;
    }

    p {
      margin: 1em 0;
      color: rgb(166, 166, 166);

    }

    a {
      color: var(--primary-color);
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }

    code {
      font-family: Consolas, Monaco, 'Andale Mono', monospace;
    }

    /* 代码块样式 */
    .hljs {
      display: flex;
      flex-direction: column;
      counter-reset: line;
      padding: 10px 0 0 16px;
      border-radius: 6px;
      margin-bottom: 15px;

      ol {
        list-style-type: none;
        padding: 0;
        transition: max-height 0.3s ease-in-out;
        padding-bottom: 15px;
        max-height: 1000px;
        overflow: auto;
        font-size: 16px;

        li::before {
          display: inline-block;
          content: counter(line);
          counter-increment: line;
          color: #999;
          text-align: right;
          user-select: none;
          width: 20px;
          padding-right: 20px;
        }
      }

      .function-code {
        display: flex;
        justify-content: right;
        user-select: none;
        align-items: center;
        padding: 0 20px 0 10px;
        margin-bottom: 6px;

        .show-code {
          margin-right: 10px;
          transition: transform 0.2s;
        }

        .show-code,
        .copy-code {
          cursor: url('/src/assets/cursor/Hand.cur'), pointer !important;
        }

        .lang {
          display: inline-block;
          margin-right: auto;
          font-size: 18px;
          margin-bottom: 5px;
        }
      }


    }

    /* 表格样式 */
    table {
      border-collapse: collapse;
      margin: 1em 0;
      width: 100%;

      th,
      td {
        padding: 0.6em 1em;
        border: 1px solid #404549;
        text-align: center;
      }

      th {
        background-color: #2b3036;
      }
    }

    /* 任务列表 */
    .task-list-item {
      list-style-type: none;

      &-checkbox {
        margin: 0 0.2em 0.25em -1.4em;
      }
    }

    /* 图片调整 */
    img {
      max-width: 100%;
      border-radius: 8px;
      margin: 1em 0;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      margin: 15px auto;
    }

    /* 响应式表格 */
    @media screen and (max-width: 768px) {
      table {
        display: block;
        overflow-x: auto;
      }
    }
  }
}
</style>
