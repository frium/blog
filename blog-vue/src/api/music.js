import { musicRequest } from "@/utils/request";

export function getMusicAPI(server = 'netease', type = 'playlist', id) {
  return musicRequest({
    params: {
      server,
      type,
      id
    }
  })
}

