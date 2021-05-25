package com.zty.springboot01login.Utils;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.StorageV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class K8sConnect {
    /*TODO 使用.kube/config无效，好像是ip是集群内部的，所以此处写死master节点的url，若需要进行动态master节点则需要重写脚本*/
//    private static String url = "https://124.70.84.98:6443";
//    private  static String url="https://202.117.249.18:6443/";
    private static String url = "https://10.168.4.167:6443";
    private static String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFvQ0pHa3h1QXNBZnJidFQzM1BScTlRZWN6WnZnQnJheC1HYWtTR1VHblUifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJvYXJkLXVzZXItdG9rZW4tajlxZGwiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoia3Vib2FyZC11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMTdlZTg2YWQtOGQzZi00ZjNkLWIwYWEtM2QxM2YzMmYzMGYyIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmt1Ym9hcmQtdXNlciJ9.X_SQ8QOwcgNRXhGw29lSQHPPhqE9ddvi_6V4HWNs0CBsGfDofprBixKq9luHEsilnO1f8JtcTdK357UExUXqAfZ3UPWBw2JQTkHK11-CG8n21rm361TrvO9DVi1_0JR33Xi4F3ZuFU5ZvUKCpn6mTAA_5PoGLWsi7p_3qNtEfbPkTQiF56OII7ai7ifWXnQvPDXiXgTs51UrIYKK45AIb81B3uIpXHChBG6lcwn-PBfI7w_bOB2J5zwnG7nfPAPEsi5BDcLgF-8m0KYkaT30Vsh2lQFbv3JfnVj3cK-6j3VtWH1a9GFp6NpflHQw3R9SYV5HBob7ZFupU0VT7IzlOg";
    public static String deploymentPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createDeployment.yaml";
    public static String podPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createPod.yaml";
    public static String servicePath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createService.yaml";
    public static String pvPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createLocalpv.yaml";
    public static String pvcPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createLocalpvc.yaml";
    public static String storageclassPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createStorageclass.yaml";

    /*初始化client连接*/
    static {
        System.out.println(url);
        System.out.println(token);
        ApiClient client = new ClientBuilder().setBasePath(url).setVerifyingSsl(false).setAuthentication(new AccessTokenAuthentication(token)).build();
        Configuration.setDefaultApiClient(client);
//        try {
//            ApiClient client=ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("src/main/java/com/zty/springboot01login/Utils/admin.conf"))).build();
//            client.setBasePath(url);
//            Configuration.setDefaultApiClient(client);
//            System.out.println(client.getBasePath());;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /*Pod操作*/
    /* *
     * @描述：加载yaml配置文件
     * @param path
     * @return java.lang.Object
     * @author zty
     */
    public static Object loadYaml(String path) throws Exception {
        Reader reader = new FileReader(path);
        File file = new File(path);
        return Yaml.load(file);
    }

    /* *
     * @描述：创建pod
     * @param nameSpace 命名空间
     * @param body  pod体
     * @return io.kubernetes.client.models.V1Pod
     * @author zty
     */
    public static V1Pod creatPod(String nameSpace, V1Pod body) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        return new CoreV1Api().createNamespacedPod(nameSpace, body, "true", null, null);
    }

    /* *
     * @描述：删除pod
     * @param nameSpace 命名空间
     * @param podName pod名字
     * @return void
     */
    public static void deletePod(String nameSpace, String podName) throws Exception {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().deleteNamespacedPod(podName, nameSpace, "true",
                null, null, null, null, null);
    }

    /* *
     * @描述：通过pod名称的部分匹配得到pod
     * @param nameSpace
     * @param podName
     * @return io.kubernetes.client.models.V1Pod
     */
    public static V1Pod getPodByName(String nameSpace, String podName) throws Exception {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        V1PodList aTrue = new CoreV1Api().listNamespacedPod(nameSpace, "true", null, null, null, null, null, null, null);
        for (V1Pod pod : aTrue.getItems()) {
            if (pod.getMetadata().getName().contains(podName)) {
                return pod;
            }
        }
        return null;
//        return new CoreV1Api().readNamespacedPod(podName, nameSpace, "true", null, null);
    }

    /*Service操作*/
    /* *
     * @描述：创建service
     * @param nameSpace 命名空间
     * @param sv service体
     * @return void
     */
    public static void createService(String nameSpace, V1Service sv) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().createNamespacedService(nameSpace, sv, "true", null, null);
    }

    /* *
     * @描述：删除service
     * @param nameSpace 命名空间
     * @param serviceName  service名
     * @return void
     */
    public static void deleteService(String nameSpace, String serviceName) throws Exception {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().deleteNamespacedService(serviceName, nameSpace, null, null, null, null, null, null);
    }

    /* *
     * @描述：得到service
     * @param nameSpace
     * @param serviceName
     * @return io.kubernetes.client.models.V1Service
     */
    public static V1Service getServiceByName(String nameSpace, String serviceName) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        return new CoreV1Api().readNamespacedService(serviceName, nameSpace, null, null, null);
    }

    /*Deployment操作*/
    /* *
     * @描述：创建deployment
     * @param nameSpace
     * @param body
     * @return void
     */
    public static void createDeployment(String nameSpace, V1Deployment body) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new AppsV1Api().createNamespacedDeployment(nameSpace, body, "true", null, null);
    }

    /* *
     * @描述：删除deployment
     * @param nameSpace
     * @param deployeName
     * @return void
     */
    public static void deleteDeployment(String nameSpace, String deployeName) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new AppsV1Api().deleteNamespacedDeployment(deployeName, nameSpace, "true", null, null, null, null, null);
    }

    /* *
     * @描述:通过namespace和deploymentname查找Deployment
     * @param namespace
     * @param deployeName
     * @return io.kubernetes.client.models.V1Deployment
     */
    public static V1Deployment getDeploymentByName(String nameSpace, String deployeName) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        System.out.println(deployeName);
//        return new AppsV1Api().readNamespacedDeployment(deployeName, nameSpace, "true", null, null);
        return new AppsV1Api().readNamespacedDeploymentStatus(deployeName, nameSpace, "true");
    }

    /* *
     * @描述：通过RS名称得到RS
     * @param nameSpace
     * @param name
     * @return io.kubernetes.client.models.V1ReplicaSet
     */
    public static V1ReplicaSet getReplicaSetByName(String nameSpace, String name) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        return new AppsV1Api().readNamespacedReplicaSet(name, nameSpace, "true", null, null);
    }

    /* *
     * @描述：如果namespace是null，就传回"default"
     * @param nameSpace
     * @return java.lang.String
     */
    public static String nameSpaceNullToDefault(String nameSpace) {
        if (nameSpace == null) {
            return "default";
        }
        return nameSpace;
    }

//    public static String saveDeploy

    /*just a test -_-*/
    public void test() throws IOException, ApiException {
//        CoreV1Api api = new CoreV1Api();
//        // invokes the CoreV1Api client
//        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null);
//        for (V1Pod item : list.getItems()) {
//            System.out.println(item.getMetadata().getName());
//        }
//        V1NamespaceList aTrue = api.listNamespace("true", null, null, null, null, null, null, null);
//
//        for (V1Namespace namespace : aTrue.getItems()) {
//            System.out.println(namespace);
//        }
//        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api();
////        apiInstance.deleteNamespacedDeployment("web","default",null,null,null,null,null,null);
//        ExtensionsV1beta1DeploymentList extensionsV1beta1DeploymentList = apiInstance.listNamespacedDeployment("default", "true", null, null, null, null, null, null, null);
//        for (ExtensionsV1beta1Deployment e : extensionsV1beta1DeploymentList.getItems()) {
//            System.out.println(e);
//        }

//        try {
//            ExtensionsV1beta1Api apiInstance2 = new ExtensionsV1beta1Api();
//            V1Status v1Status = apiInstance2.deleteNamespacedDeployment("web", "default", "true", null, null, null, null, null);
//            System.out.println(v1Status);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
        try {
            V1Deployment deployment = (V1Deployment) loadYaml("src/main/java/com/zty/springboot01login/Utils/dorowu4.yaml");
            createDeployment("default", deployment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*创建pv*/
    public static void createPv(String nameSpace, V1PersistentVolume pv) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().createPersistentVolume(pv, "true", null, null);
    }

    /*查找pv*/
    public static V1PersistentVolume getPvByName(String nameSpace, String name) throws ApiException {
        return new CoreV1Api().readPersistentVolume(name,"true",null,null);
    }

    /*创建pvc*/
    public static void createPvc(String nameSpace, V1PersistentVolumeClaim pvc) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().createNamespacedPersistentVolumeClaim(nameSpace, pvc, "true", null, null);
    }

    /*查找pvc*/
    public static V1PersistentVolumeClaim getPvcByName(String nameSpace, String name) throws ApiException {
        return new CoreV1Api().readNamespacedPersistentVolumeClaim(name, nameSpace, "true", null, null);
    }

    /*创建StorageClass*/
    public static void createStorageClass(String nameSpace, V1StorageClass stg) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        new StorageV1Api().createStorageClass(stg, "true", null, null);
    }

    /*创建StorageClass*/
    public static V1StorageClass getStorageClassByName(String nameSpace, String name) throws ApiException {
        nameSpace = nameSpaceNullToDefault(nameSpace);
        return new StorageV1Api().readStorageClass(name, "true", null, null);
    }
}
