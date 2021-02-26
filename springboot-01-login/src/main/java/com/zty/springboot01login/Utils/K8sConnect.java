package com.zty.springboot01login.Utils;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1NamespaceList;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

import java.io.FileReader;
import java.io.IOException;

public class K8sConnect {
    private static String ip="124.70.84.98";
    private static int port=22;
    /*TODO 使用.kube/config无效，好像是ip是集群内部的，所以此处写死master节点的url，若需要进行动态master节点则需要重写脚本*/
    private static String url="https://124.70.84.98:6443";
    private static String token="eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJkYXNoYm9hcmQtYWRtaW4tdG9rZW4tYjI3ZjQiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGFzaGJvYXJkLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMzY3NDhkN2UtNzFmOC0xMWViLWE2MTAtZmExNjNlMjY2MDQ4Iiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmRhc2hib2FyZC1hZG1pbiJ9.irCWDuRkQ4y7DKof32np2Ybdeig5DXaXsR42FRV2qfvjq_-3TJyBkoIPXt-QLiFoN51Ke14wurHYs8EXSNnpWO3myF9R_99E674qQYPJSfZaFhUC268qeuH2M-sbjTr12unRfLZ8UjXGyb97nnGfYf-Dp1f1RS_cXSMgTQ19wYcfb7E-T20_PXJ1Z1y99d3rgftYm7Z1g5UymK6DtUDiv0VR270fP2cWEudNuAPl_-sNIqnb7a3h7H-RULJHIYPQVKqIaO6cDqqyJXczhzsd4nfEzWKcT8fMORgZxGwLVb6FVkDT4cMlNQqtSyQ6I0gnr5qWbYsi__SbEuZS1SwXQQ";
//    private String filename="src/main/java/com/zty/springboot01login/Utils/k8s.conf";
    public void test() throws IOException, ApiException {
        ApiClient client = new ClientBuilder().setBasePath(url).setVerifyingSsl(false).setAuthentication(new AccessTokenAuthentication(token)).build();
        Configuration.setDefaultApiClient(client);
        System.out.println(client.getBasePath());
        CoreV1Api api = new CoreV1Api();

        // invokes the CoreV1Api client
        V1NamespaceList v1NamespaceList = api.listNamespace(null, null, null, null, null, null, null, null);
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
    }
}
