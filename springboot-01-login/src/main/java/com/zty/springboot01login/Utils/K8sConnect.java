package com.zty.springboot01login.Utils;

import com.google.gson.Gson;
import com.google.protobuf.Api;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.proto.V1Apps;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Yaml;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import javafx.beans.DefaultProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

@Component
public class K8sConnect {
    /*TODO 使用.kube/config无效，好像是ip是集群内部的，所以此处写死master节点的url，若需要进行动态master节点则需要重写脚本*/
    private static String url = "https://124.70.84.98:6443";
    private static String token = "";
    public static String deploymentPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createDeployment.yaml";
    public static String podPath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createPod.yaml";
    public static String servicePath = "springboot-01-login/src/main/java/com/zty/springboot01login/Utils/createService.yaml";

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
        nameSpace=nameSpaceNullToDefault(nameSpace);
        return new CoreV1Api().createNamespacedPod(nameSpace, body, "true", "true", null);
    }

    /* *
     * @描述：删除pod
     * @param nameSpace 命名空间
     * @param podName pod名字
     * @return void
     */
    public static void deletePod(String nameSpace, String podName) throws Exception {
        nameSpace=nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().deleteNamespacedPod(podName, nameSpace, "true",
                null, null, null, null, null);
    }

    /*Service操作*/
    /* *
     * @描述：创建service
     * @param nameSpace 命名空间
     * @param sv service体
     * @return void
     */
    public static void createService(String nameSpace, V1Service sv) throws ApiException {
        nameSpace=nameSpaceNullToDefault(nameSpace);
        new CoreV1Api().createNamespacedService(nameSpace, sv, "true", null, null);
    }

    /* *
     * @描述：删除service
     * @param nameSpace 命名空间
     * @param serviceName  service名
     * @return void
     */
    public static void deleteService(String nameSpace, String serviceName) throws Exception {
        nameSpace=nameSpaceNullToDefault(nameSpace);
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
        nameSpace=nameSpaceNullToDefault(nameSpace);
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
        return new AppsV1Api().readNamespacedDeployment(deployeName, nameSpace, "true", null, null);
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
}
