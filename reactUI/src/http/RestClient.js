import axios from 'axios';
import vars from '../.env';

export default class RestClient {
  protocol;
  domain;
  port;
  instance;

  constructor() {
    // Get Values From environment
    this.protocol = vars.API_PROTOCOL;
    this.domain = vars.API_DOMAIN;
    this.port = vars.API_PORT;

    // Create axios instance
    this.instance = axios.create({
      baseURL: `${this.protocol}://${this.domain}:${this.port}`,
      timeout: 60000,
    });
  }
}
