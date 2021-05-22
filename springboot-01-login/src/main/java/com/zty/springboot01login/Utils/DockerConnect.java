package com.zty.springboot01login.Utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;

/* *
 * @描述：docker 连接和原生操作
 * @param null
 * @return
 * @author：zty
 */
public class DockerConnect {
    static DockerClient client;
//    static String ip = "tcp://10.168.4.167:2375";
    static  String ip = "tcp://202.117.249.18:2375";
    static {
//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerTlsVerify(true)
//                .withDockerCertPath("springboot-01-login/src/main/resources/dockercerts").withDockerHost(ip)
//                .withDockerConfig("springboot-01-login/src/main/resources/dockercerts").withApiVersion("1.40").withRegistryUrl("https://index.docker.io/v1/")
//                .withRegistryUsername("xx").withRegistryPassword("Zty981115")
//                .withRegistryEmail("zty2804@163.com").build();
//                .withDockerCertPath("springboot-01-login/src/main/resources/dockercerts")
//                .withApiVersion("1.40").withRegistryUrl("https://index.docker.io/v1/")
//                .withRegistryUsername("root").withRegistryPassword("Zty981115")
//                .withDockerHost(ip)
//                .withDockerConfig("springboot-01-login/src/main/resources/dockercerts")
//                .build();
        client = DockerClientBuilder.getInstance(ip).build();
    }

    /* *
     * @描述：创建容器
     * @param imageName
     * @return com.github.dockerjava.api.command.CreateContainerResponse
     */
    public static CreateContainerResponse createContainers(String imageName) {
        // 暴露的端口
        ExposedPort tcp80 = ExposedPort.tcp(80);
        Ports portBindings = new Ports();
        // 绑定主机随机端口 -> docker服务器5000端口
        portBindings.bind(tcp80, Ports.Binding.empty());
        CreateContainerResponse container = client.createContainerCmd(imageName)
                .withPortBindings(portBindings)
                .withExposedPorts(tcp80).exec();
        return container;
    }

    /* *
     * @描述：启动容器
     * @param containerId
     * @return void
     */
    public static void startContainer(String containerId) {
        containerId = formatContainerId(containerId);
        client.startContainerCmd(containerId).exec();
    }

    /* *
     * @描述：删除容器
     * @param containerId
     * @return void
     */
    public static void removeContainer(String containerId) {
        containerId = formatContainerId(containerId);
        client.removeContainerCmd(containerId).exec();
    }

    /* *
     * @描述：删除镜像
     * @param containerId
     * @return void
     */
    public static void removeImage(String imageId) {
        client.removeContainerCmd(imageId).exec();
    }

    public static boolean isPasue(String containerId) {
        containerId = formatContainerId(containerId);
        return true;
    }

    /* *
     * @描述：暂停容器
     * @param containerId
     * @return void
     */
    public static void pasueContainer(String containerId) {
        containerId = formatContainerId(containerId);
        client.pauseContainerCmd(containerId).exec();
    }

    /* *
     * @描述：取消暂停
     * @param containerId
     * @return void
     */
    public static void unpasueContainer(String containerId) throws Exception {
        containerId = formatContainerId(containerId);
        client.unpauseContainerCmd(containerId).exec();
    }

    /* *
     * @描述：保存容器
     * @param containerId
     * @return void
     */
    public static String commitContainer(String containerId, String imageName, String tag) {
        containerId = formatContainerId(containerId);
        String exec = client.commitCmd(containerId).exec();
        String imageId = formatImageId(exec);
        System.out.println(imageId);
        System.out.println(inspectImage(imageId));
        client.tagImageCmd(imageId, imageName, tag).exec();
        /*此处返回image的id*/
        return imageId;
    }

    /* *
     * @描述：获取容器信息
     * @param containerId
     * @return com.github.dockerjava.api.command.InspectContainerResponse
     */
    public static InspectContainerResponse inspectContainer(String containerId) {
        containerId = formatContainerId(containerId);
        InspectContainerResponse exec = client.inspectContainerCmd(containerId).exec();
        return exec;
    }

    /* *
     * @描述：获取镜像信息
     * @param imageId
     * @return com.github.dockerjava.api.command.InspectImageResponse
     */
    public static InspectImageResponse inspectImage(String imageId) {
        imageId = formatImageId(imageId);
        InspectImageResponse exec = client.inspectImageCmd(imageId).exec();
        return exec;
    }

    /* *
     * @描述：重构containerid，因为k8s返回的containerid带一个docker头
     * @param containerId
     * @return java.lang.String
     */
    public static String formatContainerId(String containerId) {
        if (containerId != null) {
            if (containerId.startsWith("docker://")) {
                containerId = containerId.substring(9);
            }
        }
        return containerId;
    }

    /* *
     * @描述：重构imageid，印务docker commit返回的imageid带有一个sha256头
     * @param imageId
     * @return java.lang.String
     */
    public static String formatImageId(String imageId) {
        if (imageId != null) {
            if (imageId.startsWith("sha256:")) {
                imageId = imageId.substring(7);
            }
        }
        return imageId;
    }
}
