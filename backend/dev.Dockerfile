FROM eclipse-temurin:25

USER root

RUN apt-get update && apt-get install -y \
    git \
    curl \
    unzip \
    zsh \
    vim \
    openssh-client \
    wget \
    groovy

# Node.js 20 + npm
RUN curl -fsSL https://deb.nodesource.com/setup_24.x | bash -

RUN apt-get install -y nodejs

# Gradle
ARG GRADLE_VERSION=9.1.0

RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && ln -s /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip

# Usuário seguro
ARG UID=501

#RUN groupadd -g ${GID} dev || true
#RUN useradd -m -u ${UID} -g ${GID} -s /bin/bash dev
RUN useradd -m -u ${UID} dev

RUN mkdir -p /home/dev/.ssh && \
    chmod 700 /home/dev/.ssh && \
    mkdir -p /home/dev/.npm-global && \
    mkdir -p /home/dev/.claude &&  \
    chown -R dev:dev /home/dev

USER dev

# npm global no HOME do usuário
ENV NPM_CONFIG_PREFIX=/home/dev/.npm-global
ENV PATH=$PATH:/home/dev/.npm-global/bin

# Instala Codex
RUN npm install -g pnpm
RUN npm install -g @anthropic-ai/claude-code

RUN ssh-keyscan github.com >> /home/dev/.ssh/known_hosts
RUN chmod 644 /home/dev/.ssh/known_hosts

WORKDIR /home/dev/icr-church/backend