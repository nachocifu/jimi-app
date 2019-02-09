import axios from 'axios';
import conf from '../conf'

export default class RestClient {
  protocol;
  domain;
  port;
  instance;

  constructor(token = null) {
    // Get Values From environment
    this.protocol = conf.API_PROTOCOL;
    this.domain = conf.API_DOMAIN;
    this.port = conf.API_PORT;

    // Handle Headers
    const headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      'X-AUTH-TOKEN': token
      };

    // Create axios instance
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}`,
      timeout: 60000,
      headers: headers,
    });
  }
}
