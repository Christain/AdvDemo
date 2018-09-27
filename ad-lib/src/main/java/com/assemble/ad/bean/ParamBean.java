package com.assemble.ad.bean;

public class ParamBean {


    /**
     * request : {"sign":"xxxxxx","app_id":"5PaYZJh1bHTMwXQH","security_key":"8Q446DMMlJHCqDPQ","ssp":"aiclk"}
     * param : {"media":{"type":1,"app":{"package_name":"InternetRadio.all","app_version":"1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}},"client":{"type":3,"version":"1.6.4"},"device":{"id_idfa":"ssss","id_imei":"xxxx","height":1136,"width":640,"brand":"apple","model":"iPhone6","os_version":"1.1.2","os_type":1},"adslot":{"id":"5000188","type":1,"height":100,"width":640},"network":{"type":1,"ip":"218.66.169.71"}}
     */

    private RequestBean request;
    private ParamEntity param;

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public ParamEntity getParam() {
        return param;
    }

    public void setParam(ParamEntity param) {
        this.param = param;
    }

    public static class RequestBean {
        /**
         * sign : xxxxxx
         * app_id : 5PaYZJh1bHTMwXQH
         * security_key : 8Q446DMMlJHCqDPQ
         * ssp : aiclk
         */

        private String sign;
        private String app_id;
        private String security_key;
        private String ssp;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getSecurity_key() {
            return security_key;
        }

        public void setSecurity_key(String security_key) {
            this.security_key = security_key;
        }

        public String getSsp() {
            return ssp;
        }

        public void setSsp(String ssp) {
            this.ssp = ssp;
        }
    }

    public static class ParamEntity {
        /**
         * media : {"type":1,"app":{"package_name":"InternetRadio.all","app_version":"1.2.3"},"site":{"domain":"xx","urls":"xx","title":"xx","keywords":"xx"},"browser":{"user_agent":"xx"}}
         * client : {"type":3,"version":"1.6.4"}
         * device : {"id_idfa":"ssss","id_imei":"xxxx","height":1136,"width":640,"brand":"apple","model":"iPhone6","os_version":"1.1.2","os_type":1}
         * adslot : {"id":"5000188","type":1,"height":100,"width":640}
         * network : {"type":1,"ip":"218.66.169.71"}
         */

        private MediaEntity media;
        private ClientEntity client;
        private DeviceEntity device;
        private AdslotEntity adslot;
        private NetworkEntity network;

        public MediaEntity getMedia() {
            return media;
        }

        public void setMedia(MediaEntity media) {
            this.media = media;
        }

        public ClientEntity getClient() {
            return client;
        }

        public void setClient(ClientEntity client) {
            this.client = client;
        }

        public DeviceEntity getDevice() {
            return device;
        }

        public void setDevice(DeviceEntity device) {
            this.device = device;
        }

        public AdslotEntity getAdslot() {
            return adslot;
        }

        public void setAdslot(AdslotEntity adslot) {
            this.adslot = adslot;
        }

        public NetworkEntity getNetwork() {
            return network;
        }

        public void setNetwork(NetworkEntity network) {
            this.network = network;
        }

        public static class MediaEntity {
            /**
             * type : 1
             * app : {"package_name":"InternetRadio.all","app_version":"1.2.3"}
             * site : {"domain":"xx","urls":"xx","title":"xx","keywords":"xx"}
             * browser : {"user_agent":"xx"}
             */

            private int type;
            private AppEntity app;
            private SiteEntity site;
            private BrowserEntity browser;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public AppEntity getApp() {
                return app;
            }

            public void setApp(AppEntity app) {
                this.app = app;
            }

            public SiteEntity getSite() {
                return site;
            }

            public void setSite(SiteEntity site) {
                this.site = site;
            }

            public BrowserEntity getBrowser() {
                return browser;
            }

            public void setBrowser(BrowserEntity browser) {
                this.browser = browser;
            }

            public static class AppEntity {
                /**
                 * package_name : InternetRadio.all
                 * app_version : 1.2.3
                 */

                private String package_name;
                private String app_version;

                public String getPackage_name() {
                    return package_name;
                }

                public void setPackage_name(String package_name) {
                    this.package_name = package_name;
                }

                public String getApp_version() {
                    return app_version;
                }

                public void setApp_version(String app_version) {
                    this.app_version = app_version;
                }
            }

            public static class SiteEntity {
                /**
                 * domain : xx
                 * urls : xx
                 * title : xx
                 * keywords : xx
                 */

                private String domain;
                private String urls;
                private String title;
                private String keywords;

                public String getDomain() {
                    return domain;
                }

                public void setDomain(String domain) {
                    this.domain = domain;
                }

                public String getUrls() {
                    return urls;
                }

                public void setUrls(String urls) {
                    this.urls = urls;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }
            }

            public static class BrowserEntity {
                /**
                 * user_agent : xx
                 */

                private String user_agent;

                public String getUser_agent() {
                    return user_agent;
                }

                public void setUser_agent(String user_agent) {
                    this.user_agent = user_agent;
                }
            }
        }

        public static class ClientEntity {
            /**
             * type : 3
             * version : 1.6.4
             */

            private int type;
            private String version;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }
        }

        public static class DeviceEntity {
            /**
             * id_idfa : ssss
             * id_imei : xxxx
             * height : 1136
             * width : 640
             * brand : apple
             * model : iPhone6
             * os_version : 1.1.2
             * os_type : 1
             */

            private String id_idfa;
            private String id_imei;
            private int height;
            private int width;
            private String brand;
            private String model;
            private String os_version;
            private int os_type;

            public String getId_idfa() {
                return id_idfa;
            }

            public void setId_idfa(String id_idfa) {
                this.id_idfa = id_idfa;
            }

            public String getId_imei() {
                return id_imei;
            }

            public void setId_imei(String id_imei) {
                this.id_imei = id_imei;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getOs_version() {
                return os_version;
            }

            public void setOs_version(String os_version) {
                this.os_version = os_version;
            }

            public int getOs_type() {
                return os_type;
            }

            public void setOs_type(int os_type) {
                this.os_type = os_type;
            }
        }

        public static class AdslotEntity {
            /**
             * id : 5000188
             * type : 1
             * height : 100
             * width : 640
             */

            private String id;
            private int type;
            private int height;
            private int width;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }

        public static class NetworkEntity {
            /**
             * type : 1
             * ip : 218.66.169.71
             */

            private int type;
            private String ip;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }
    }
}
