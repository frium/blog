import { musicRequest } from "@/utils/request";

export const getMusicAPI = (server = 'netease', type = 'playlist', id) => {
  return musicRequest({
    params: {
      server,
      type,
      id
    }
  })
}

