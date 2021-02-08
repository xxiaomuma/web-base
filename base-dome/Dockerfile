FROM maven:3-jdk-8

MAINTAINER xiaomuma

ENV JAR_PACKAGE_NAME = "base-dome-1.0-SNAPSHOT"
ENV DATA_DIR = "/data"
ENV WORK_DIR = "/app"
ENV INSTANCE_INDEX = 0
ENV PROFILE = "test"
ENV PORT = 8080
ENV JAVA_OPTS = ""
ENV D_OPTS = ""
ENV TZ = Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN echo 'LANG="en_US.UTF-8"' >> /etc/profile && bash /etc/profile

RUN mkdir -p "${WORK_DIR}"
ADD ./target/${JAR_PACKAGE_NAME} ${WORK_DIR}/app.jar

VOLUME ${DATA_DIR}

EXPOSE ${PORT}

CMD java ${JAVA_OPTS} \
-Djava.security.egd=file:/dev/./urandom \
-jar \
-Dspring.profiles.active=${PROFILE} \
-Dserver.port=${PORT} \
-DSERVICE_LOG_FOLDER=${DATA_DIR} \
-DSERVICE_INSTANCE_INDEX=${INSTANCE_INDEX} \
${D_OPTS} \
${WORK_DIR}/app.jar


